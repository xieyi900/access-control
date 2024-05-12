package com.management.access.control.interceptor;

import com.management.access.control.util.Base64Util;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.management.access.control.util.AccessConstant.ROLE_ADMIN;
import static com.management.access.control.util.AccessConstant.ROLE_USER;

public class UserAuthorizationInterceptor implements HandlerInterceptor {

    public static final String EMPTY_STRING = "";
    private static final Logger logger = LoggerFactory.getLogger(UserAuthorizationInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("UserAuthorizationInterceptor start to decode authheader");
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null) {
            try {
                String decoded = Base64Util.decodeBase64(authHeader);
                String[] decodeArray = decoded.split(":");
                if(decodeArray.length < 3){
                    response.sendError(HttpServletResponse.SC_LENGTH_REQUIRED, "Invalid Authorization Header");
                    return false;
                }

                request.setAttribute("userId", decodeArray[0]);
                request.setAttribute("role", decodeArray[2]);
            } catch (Exception e) {
                // Invalid Authorization header, send unauthorized response
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Authorization header");
                return false; // Do not continue with the handler
            }
        } else {
            // Authorization header missing, send unauthorized response
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Authorization header missing");
            return false; // Do not continue with the handler
        }

        return true;
    }

    private String getRequiredRole(HttpServletRequest request){
        String requestUrl = request.getRequestURI();
        if(requestUrl.startsWith("/admin")){
            return ROLE_ADMIN;
        }else if(requestUrl.startsWith("/user")){
            return ROLE_USER;
        }

        return EMPTY_STRING;
    }
}
