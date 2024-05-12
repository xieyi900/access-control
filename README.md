# Project Title

Access Control Management System

## Overview

There are 2 kinds of roles in this system: admin & user. We use a header encoded in
Base64 to pass the role info into all urls of this system. This header should be decoded in all
requests to check who is accessing the endpoint.
Decoded header could be :

{
"userId":123456,
"accountName": "XXXXXXX",
"role": "admin"
}

## Features

Considering the common feature that each API will have a Authorization header 
to encode with UserId, AccountNumber and Role so a RoleInfoInterceptor is designed
to decode the header and verify it include all fields we need.

Additionally, there are two APIs to implement the requirement:
1. /add/addUser for admins which could add accesses for users. 
   Non-admin accounts will get an error message as they have no access to this
   endpoint. POST body should contain:
   {
      "userId": 123456,
      "endpoints": [
          "resource A",
          "resource B",
          "resource C"
          ]
   }
   
   That means 123456 is granted to access three resources. We will save the details in a json file for persistence purpose.

2. /user/{resource} for users. This API is to check whether the user has access to the given resource.

## Core methods
 1. Add User Access
 
Here we load the data from access.json after the instantiation of UserAccessService bean.
and put the data into a userAccessMap that will be utilized to save the related endpoints 
once we need to add user access. Since there will be a case that sometimes it may cause 
a amount of time if too many userIds in the map, we use the threadpool and Compleatable 
features to asynchronously executing the writeFile task so that it will not block the 
main thread. Besides, we add a callback interface to notify whenever the success of failure 
on the task.

public ResponseEntity<Object> addUserAccess(String userId, List<String> endpoints, WriteCallBack callBack) {
logger.info("[Management System] adding resources {} with user id: {}", endpoints, userId);
userAccessMap.put(userId, endpoints);

        CompletableFuture.runAsync(() -> {
            try{
                saveAccessInfo();
                callBack.onSuccess(userId);
            } catch (Exception e) {
                e.printStackTrace();
                callBack.onFailure(userId, e);
            }
        }, executor);

        return new ResponseEntity<>(Response.ok("User Access added success."), HttpStatus.OK);
    }

2. Check access of User
Considering we put the all the information in a map during the init and addUser processes.
We could filter this map by comparing whether the given resource matches or not.
   public ResponseEntity<Object> checkUserAccess(String userId, String resource) throws BaseException {
   logger.info("[Management System] checking reource access with user id: {}",  userId);
   boolean hasAccess = false;
   if(!userAccessMap.isEmpty() && userAccessMap.containsKey(userId)){
   hasAccess =  userAccessMap.get(userId)
   .stream()
   .anyMatch(endpoint -> endpoint.equals(resource));
   }

        if(!hasAccess){
            throw new AccessException(ERROR_CODE_9001,"User "+userId + " has no access for "+resource );
        }
        return new ResponseEntity<>(Response.ok("User "+userId + " check access success"), HttpStatus.OK);
   }

## Points of Improvement
1. In the project spring security framework is not used for role validation.
We could consider to use spring security to improve the validation feature.
2. There will cause thread-safe issue in the UserAccessService since we added
a map global variable. it may be a solution to utilize the threadlocal varaiable
but I am not sure if some better ways could replace.