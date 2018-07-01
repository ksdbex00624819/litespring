package com.like.zero.spring.beans;

/**
 * Created by like
 * 2018/7/1
 */
public interface TypeConverter {
    <T> T convertIfNecessary(Object value, Class<T> requiredType) throws TypeMismatchException;
}
