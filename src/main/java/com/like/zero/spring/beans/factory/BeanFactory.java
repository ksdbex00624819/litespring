package com.like.zero.spring.beans.factory;

import com.like.zero.spring.beans.BeanDefinition;

/**
 * Created by like
 * 2018/6/9
 */
public interface BeanFactory {
    BeanDefinition getBeanDefinition(String beanId);

    Object getBean(String beanId);

}
