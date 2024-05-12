package com.management.access.control.util;

import io.micrometer.common.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * root context
 * @author ben
 */
public class RootContext {

    public static final ThreadLocalContextHolder CONTEXT_HOLDER = ThreadLocalContextHolder.getInstance();

    public static List<String> getEndpoints(String userId){
       return CONTEXT_HOLDER.entries().containsKey(userId) ?(List<String>) CONTEXT_HOLDER.get(userId) : Collections.EMPTY_LIST;
    }

    public static void bind(String userId, List<String> endpoints){
        if(!StringUtils.isBlank(userId)){
            CONTEXT_HOLDER.put(userId, endpoints);
        }
    }

    public static void unbind(String userId){
        if(!StringUtils.isBlank(userId)){
            CONTEXT_HOLDER.remove(userId);
        }
    }

    public static Map<String, Object> entries(){
        return CONTEXT_HOLDER.entries();
    }

}
