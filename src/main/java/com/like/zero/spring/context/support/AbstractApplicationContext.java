package com.like.zero.spring.context.support;

import com.like.zero.spring.beans.factory.support.DefaultBeanFactory;
import com.like.zero.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.like.zero.spring.context.ApplicationContext;
import com.like.zero.spring.core.io.DefaultResourceLoader;
import com.like.zero.spring.core.io.Resource;

/**
 * Created by like
 * 2018/6/24
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ApplicationContext {

    private DefaultBeanFactory defaultBeanFactory;

    public AbstractApplicationContext(String configFile) {
        defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultBeanFactory);
        Resource resource = getResourceByPath(configFile);
        xmlBeanDefinitionReader.loadBeanDefinition(resource);
        defaultBeanFactory.setBeanClassLoader(getClassLoader());
    }

    protected abstract Resource getResourceByPath(String configFile);

    @Override
    public Object getBean(String beanId) {
        return defaultBeanFactory.getBean(beanId);
    }


}
