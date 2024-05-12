package com.management.access.control.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.StandardCharsets;

/**
 * Base64 encode and decode
 * @author xieyi
 */
public class Base64Util {

    public static String encodeBase64String(String str){
        byte[] bytes = str.getBytes(StandardCharsets.UTF_8);
        return Base64.encodeBase64String(bytes);
    }

    public static String decodeBase64(String str){
        byte[] bytes = Base64.decodeBase64(str);
        return new String(bytes, StandardCharsets.UTF_8);
    }
}
