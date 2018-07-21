package com.like.zero.spring.beans;

/**
 * Created by like 2018/6/12
 */
public class BeansException extends RuntimeException {

    public BeansException(String message) {
        super(message);
    }

    public BeansException(String message, Throwable cause) {
        super(message, cause);
    }
}
