package com.management.access.control.util;

public class AccessConstant {

    public static final String ACCESS_FILE = "access.json";
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_USER = "USER";
    public static final Integer ERROR_CODE_9001 = 9001;
    public static final Integer ERROR_CODE_9002 = 9002;
    public static final Integer ERROR_CODE_9003 = 9003;
    public static final int DEFAULT_THREAD_COUNT =
            Runtime.getRuntime().availableProcessors() > 1 ? Runtime.getRuntime().availableProcessors() / 2 : 1;
}
