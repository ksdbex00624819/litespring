package com.like.zero.spring.beans.factory.support;

import com.like.zero.spring.beans.factory.BeanFactory;
import com.like.zero.spring.beans.factory.config.RuntimeBeanReference;
import com.like.zero.spring.beans.factory.config.TypedStringValue;

/**
 * Created by like 2018/7/1
 */
public class BeanDefinitionValueResolver {

    private final BeanFactory beanFactory;

    public BeanDefinitionValueResolver(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object resolveValueIfNecessary(Object value) {
        if (value instanceof RuntimeBeanReference) {
            RuntimeBeanReference runtimeBeanReference = (RuntimeBeanReference) value;
            return beanFactory.getBean(runtimeBeanReference.getBeanName());
        } else if (value instanceof TypedStringValue) {
            TypedStringValue typedStringValue = (TypedStringValue) value;
            return typedStringValue.getValue();
        } else {
            // TODO
            throw new RuntimeException("the value " + value + "has not implemented");
        }
    }
}
