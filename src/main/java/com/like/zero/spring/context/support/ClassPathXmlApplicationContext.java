package com.like.zero.spring.context.support;

import com.like.zero.spring.beans.factory.support.DefaultBeanFactory;
import com.like.zero.spring.core.io.ClassPathResource;
import com.like.zero.spring.core.io.Resource;

/**
 * Created by like
 * 2018/6/24
 */
public class ClassPathXmlApplicationContext extends AbstractApplicationContext {

    public ClassPathXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String configFile) {
        return new ClassPathResource(configFile);
    }

}
