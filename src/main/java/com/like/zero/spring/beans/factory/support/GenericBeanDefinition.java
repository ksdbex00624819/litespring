package com.like.zero.spring.beans.factory.support;

import com.like.zero.spring.beans.BeanDefinition;

/**
 * Created by like
 * 2018/6/11
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id;

    private String beanClassName;

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }
}
