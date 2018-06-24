package com.like.zero.spring.beans.factory.support;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.factory.BeanDefinitionStoreException;
import com.like.zero.spring.beans.factory.NoSuchBeanDefinitionException;

/**
 * Created by like
 * 2018/6/24
 */
public interface BeanDefinitionRegistry {

    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException;

    BeanDefinition getBeanDefinition(String beanName) throws NoSuchBeanDefinitionException;
}
