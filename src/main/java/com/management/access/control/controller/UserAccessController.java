package com.management.access.control.controller;


import com.management.access.control.exception.BaseException;
import com.management.access.control.exception.AccessException;
import com.management.access.control.model.AccessInfo;
import com.management.access.control.service.UserAccessService;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.management.access.control.util.AccessConstant.ERROR_CODE_9001;
import static com.management.access.control.util.AccessConstant.ROLE_ADMIN;


/**
 * User access controller
 * @author ben
 */
@RestController
public class UserAccessController {

    @Autowired
    private UserAccessService useAccessService;

    @PostMapping("/admin/addUser")
    public ResponseEntity<Object> addUser(@RequestBody AccessInfo accessInfo, HttpServletRequest request) throws Exception {
        String role = (String)request.getAttribute("role");
        String userId = (String)request.getAttribute("userId");
        if(StringUtils.isEmpty(role) || StringUtils.isEmpty(userId)){
            throw new IllegalArgumentException("Either userId or role should be not empty");
        }

        if(!ROLE_ADMIN.equals(role)){
            throw new AccessException(ERROR_CODE_9001, "User access should be added with Admin Role");
        }
        return useAccessService.addUserAccess(userId, accessInfo);
    }

    @GetMapping("/user/{resource}")
    public ResponseEntity<Object> checkAccess(@PathVariable("resource") String resource, HttpServletRequest request) throws BaseException {
        String userId = (String)request.getAttribute("userId");
        if(StringUtils.isEmpty(userId)){
            throw new IllegalArgumentException("UserId should not be empty");
        }
        return useAccessService.checkUserAccess(userId, resource);
    }
}
