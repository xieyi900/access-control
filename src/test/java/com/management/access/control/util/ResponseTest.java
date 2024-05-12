package com.management.access.control.util;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ResponseTest {

    @Test
    public void response(){
        Response.ok();
        Response.ok("test");
        Map<String,Object> testMap = new HashMap<>();
        testMap.put("00", "success");
        Response.ok(testMap);
        Response.error();
        Response.error(500, "internal server error");
        Response.error("test");
    }
}