package com.like.zero.spring.beans.factory.config;

/**
 * Created by like 2018/6/30
 */
public class RuntimeBeanReference {

    private final String beanName;

    public RuntimeBeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
