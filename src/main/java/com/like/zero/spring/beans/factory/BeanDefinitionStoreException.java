package com.like.zero.spring.beans.factory;

import com.like.zero.spring.beans.BeansException;

/**
 * 读取XML文件出错时抛出异常
 * <p>
 * Created by like
 * 2018/6/12
 */
public class BeanDefinitionStoreException extends BeansException {

    public BeanDefinitionStoreException(String message, Throwable cause) {
        super(message, cause);
    }
}
