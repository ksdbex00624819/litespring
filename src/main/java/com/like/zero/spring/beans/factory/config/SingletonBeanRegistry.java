package com.like.zero.spring.beans.factory.config;

/**
 * Created by like
 * 2018/6/26
 */
public interface SingletonBeanRegistry {

    void registerSingleton(String beanName, Object singletonObject);

    Object getSingleton(String beanName);
}
