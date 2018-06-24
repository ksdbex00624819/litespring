package com.like.zero.spring.util;

/**
 * Created by like
 * 2018/6/24
 */
public class Assert {
    public static void notNull(Object object, String message) {
        if (object == null) {
            throw new IllegalArgumentException(message);
        }
    }
}
