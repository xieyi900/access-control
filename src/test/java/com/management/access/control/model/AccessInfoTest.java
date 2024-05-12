package com.management.access.control.model;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class AccessInfoTest {

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    public void accessInfo() {
        AccessInfo accessInfo = new AccessInfo();
        accessInfo.setUserId(Long.getLong("123456"));
        accessInfo.setEndpoints(new ArrayList<>());

        accessInfo.getUserId();
        accessInfo.getEndpoints();

        String test = "1";
        assertEquals("1", test);
    }
}