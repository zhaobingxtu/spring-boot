package com.milton.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.text.SimpleDateFormat;
import java.util.*;

public class ConstantUtils {
    private static final Logger log = LoggerFactory.getLogger(ConstantUtils.class);
    public static String[] ARGS;
    public static ConfigurableApplicationContext CONTEXT;
    public static Class<?> APPLICATION_CLASS;
    public static final boolean JSON_IGNORE_NULL = true;
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String LOCAL_TIME_FORMAT = "HH:mm:ss";
    public static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat SIMPLE_DATETIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static final SimpleDateFormat SIMPLE_LOCAL_TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    public static final TimeZone TIME_ZONE = TimeZone.getDefault();
    public static String REST_TEMPLE_TYPE;
    public static String REDIS_ON;
    public static String CUSTOM_HTTP_ERROR_HANDLE;
    public static String SSL_VERIFY_CLIENT;
    public static String COLLECT_OATH_INFO;
    public static final String CON_LOCK_KEY_PREFIX = "_conLock_";
    public static final String CON_LOCK_HEADER_PREFIX = "_conLock_header_";
    public static final String CON_LOCK_UK_PREFIX = "_conLock_uniqueKey_";
    public static final String RELEASE_CLUSTER_LOCK_PREFIX = "_release_cluster_lock_flag";
    public static final String SCHEDULER_KEY = "_$schedulers";
    public static final String SCHEDULER_TYPE = "SCHEDULER";
    public static List<String> PLAN_INSTALL;
    public static Set<String> CACHE_NAMES;
    public static Set<String> DEPEND_MODULE;
    public static Map<String, String> DEPEND_TOKEN;
    public static Set<String> SUPER_TOKENS;
    public static List<String> GATEWAY_IP;
    public static final String ABC = "abcdefghijklmnopqrstuvwxyz";
    public static final String ABC_NUM = "abcdefghijklmnopqrstuvwxyz0123456789";
    public static final int QR_CODE_SIZE = 640;
    public static final String EXTERNAL_FAIL = "EXTERNAL_SERVICE_FAIL";
    public static final String INNER_DEPEND_FAIL = "INNER_DEPEND_SERVICE_FAIL";
    public static final String BIZ_FAIL = "BIZ_SERVICE_FAIL";
    private static ThreadLocal<Map<String, Object>> threadLocal;

    public ConstantUtils() {
    }

    public static String uuid() {
        return UUID.randomUUID().toString();
    }

    public static String uuidWithoutBar() {
        return uuid().replace("-", "");
    }

    public static String getDateFormat() {
        return "yyyy-MM-dd";
    }

    public static String getTimeFormat() {
        return "yyyy-MM-dd HH:mm:ss";
    }
}
