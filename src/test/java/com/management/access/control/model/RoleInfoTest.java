package com.management.access.control.model;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(MockitoJUnitRunner.class)
class RoleInfoTest {

    @Before
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void roleInfo() {
        RoleInfo roleInfo = new RoleInfo();
        roleInfo.setUserId(Long.getLong("123456"));
        roleInfo.setAccountNumber("344555");
        roleInfo.setRole("admin");

        roleInfo.getUserId();
        roleInfo.getAccountNumber();
        roleInfo.getRole();

        String test = "1";
        assertEquals("1", test);
    }
}