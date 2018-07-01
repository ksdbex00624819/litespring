package com.like.zero.spring.beans.factory.support;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.PropertyValue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by like
 * 2018/6/11
 */
public class GenericBeanDefinition implements BeanDefinition {

    private String id;

    private String beanClassName;

    private String scope = SCOPE_DEFAULT;

    private boolean singleton = true;

    private boolean prototype = false;

    private List<PropertyValue> propertyValues = new ArrayList<>();

    public GenericBeanDefinition(String id, String beanClassName) {
        this.id = id;
        this.beanClassName = beanClassName;
    }

    @Override
    public String getBeanClassName() {
        return this.beanClassName;
    }

    @Override
    public boolean isSingleton() {
        return singleton;
    }

    @Override
    public boolean isPrototype() {
        return prototype;
    }

    @Override
    public String getScope() {
        return scope;
    }

    @Override
    public void setScope(String scope) {
        this.scope = scope;
        this.singleton = SCOPE_SINGLETON.equals(scope) || SCOPE_DEFAULT.equals(scope);
        this.prototype = SCOPE_PROTOTYPE.equals(scope);
    }

    @Override
    public List<PropertyValue> getPropertyValues() {
        return this.propertyValues;
    }
}
