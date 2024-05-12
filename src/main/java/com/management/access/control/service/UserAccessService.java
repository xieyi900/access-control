package com.management.access.control.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.management.access.control.api.WriteCallBack;
import com.management.access.control.exception.AccessException;
import com.management.access.control.exception.BaseException;
import com.management.access.control.util.AccessConstant;
import com.management.access.control.util.Response;
import com.management.access.control.model.AccessInfo;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;

import static com.management.access.control.util.AccessConstant.*;

/**
 * User Access Service for the persistence of accessInfo details
 * and the validation of resource access on the given user
 * @author ben
 */
@Service
public class UserAccessService {

    private Map<String, List<String>> userAccessMap = new ConcurrentHashMap<>();
    private static final Logger logger = LoggerFactory.getLogger(UserAccessService.class);

    private ObjectMapper mapper;

    @Autowired
    private ResourceLoader resourceLoader;

    private ExecutorService executor;

    @PostConstruct
    public void init() throws IOException {
        this.executor = new ThreadPoolExecutor(DEFAULT_THREAD_COUNT, DEFAULT_THREAD_COUNT,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        this.mapper = new ObjectMapper();
        Resource resource = resourceLoader.getResource("classpath:"+AccessConstant.ACCESS_FILE);
        if(resource.exists()){
            File file = resource.getFile();
            userAccessMap = mapper.readValue(file, new TypeReference<>() {
            });
            logger.info("userMap init success. {}", userAccessMap);
        }

    }

    /**
     * add user access
     * @param userId
     * @param accessInfo
     * @return s
     */
    public ResponseEntity<Object> addUserAccess(String userId, AccessInfo accessInfo){
        return addUserAccess(userId, accessInfo.getEndpoints(), new WriteCallBack() {
            @Override
            public void onSuccess(String userId) {
                logger.info("File Write operation for User {} completed successfully", userId);
            }

            @Override
            public void onFailure(String userId, Throwable e) {
                logger.error("File write operation for User {} failed : {}", userId, e);

            }
        });
    }

    public ResponseEntity<Object> addUserAccess(String userId, List<String> endpoints, WriteCallBack callBack) {
        logger.info("[Manager System] adding resources {} with user id: {}", endpoints, userId);
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

        return new ResponseEntity<>(Response.ok("User access addition initiated."), HttpStatus.OK);
    }

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


    private void saveAccessInfo() throws IOException {
        try(FileOutputStream fileOutputStream = new FileOutputStream("src/main/resources/access.json")){
            mapper.writeValue(fileOutputStream, userAccessMap);
            logger.info("save file success");
        }catch (IOException e) {
            logger.info("Unable to write access info accessInfo file");
            throw e;
        }
    }

}
