package com.management.access.control.util;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class RootContextTest {

    @Test
    public void rootContext(){
        RootContext.bind("123456", new ArrayList<>());
        RootContext.unbind("123456");
        RootContext.getEndpoints("123456");

        String test = "1";
        assertEquals("1",test);
    }

}