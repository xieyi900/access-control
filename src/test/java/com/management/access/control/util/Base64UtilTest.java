package com.management.access.control.util;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class Base64UtilTest {

    @Test
    public void encodeDecode(){
        Integer userId = 123456;
        String accountNumber = "123456";
        String role = "admin";

        String original = userId + ":"+ accountNumber + ":"+role;
        String encodeStr = Base64Util.encodeBase64String(original);
        Assert.assertEquals("MTIzNDU2OjEyMzQ1NjphZG1pbg==",encodeStr);

        String decodeStr = Base64Util.decodeBase64(encodeStr);
        Assert.assertEquals(original, decodeStr);

    }
}