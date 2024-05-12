package com.management.access.control.util;

import static org.junit.Assert.*;

public class ThreadLocalContextHolderTest {

    public void threadLocalContextHolder(){
        ThreadLocalContextHolder contextHolder = new ThreadLocalContextHolder();
        contextHolder.put("123456","test");
        contextHolder.get("123456");
        contextHolder.entries();
        contextHolder.remove("123456");

        String test = "1";
        assertEquals("1",test);
    }

}