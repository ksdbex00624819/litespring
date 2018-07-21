package com.like.zero.spring.beans.factory.config;

import com.like.zero.spring.beans.factory.BeanFactory;

/**
 * Created by like 2018/6/24
 */
public interface ConfigurableBeanFactory extends BeanFactory {

    void setBeanClassLoader(ClassLoader classLoader);

    ClassLoader getBeanClassLoader();
}
