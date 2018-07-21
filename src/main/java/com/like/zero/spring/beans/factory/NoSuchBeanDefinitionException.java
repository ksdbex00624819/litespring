package com.like.zero.spring.beans.factory;

import com.like.zero.spring.beans.BeansException;

/**
 * Created by like 2018/6/24
 */
public class NoSuchBeanDefinitionException extends BeansException {

    private String beanName;

    public NoSuchBeanDefinitionException(String name) {
        super("No bean named '" + name + "' available");
        this.beanName = name;
    }

}
