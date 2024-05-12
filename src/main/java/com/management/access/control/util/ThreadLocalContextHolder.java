package com.management.access.control.util;

import java.util.HashMap;
import java.util.Map;

/**
 * thread local context holder
 * @author ben
 */
public class ThreadLocalContextHolder {

    private static ThreadLocalContextHolder instance;

    private static final ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public static ThreadLocalContextHolder getInstance() {
        if(instance == null){
            synchronized (ThreadLocalContextHolder.class){
                if(instance == null){
                    instance = new ThreadLocalContextHolder();
                }
            }
        }

        return instance;

    }

    public Object put(String key, Object value) {
        return threadLocal.get().put(key, value);
    }

    public Object get(String key) {
        return threadLocal.get().get(key);
    }

    public Object remove(String key) {
        return threadLocal.get().remove(key);
    }

    public Map<String, Object> entries() {
        return threadLocal.get();
    }
}
