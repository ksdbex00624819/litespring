package com.like.zero.spring.context.support;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.factory.support.DefaultBeanFactory;
import com.like.zero.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.like.zero.spring.context.ApplicationContext;
import com.like.zero.spring.core.io.DefaultResourceLoader;
import com.like.zero.spring.core.io.Resource;
import com.like.zero.spring.util.ClassUtils;

/**
 * Created by like
 * 2018/6/24
 */
public abstract class AbstractApplicationContext extends DefaultResourceLoader implements ApplicationContext {

    private DefaultBeanFactory defaultBeanFactory;

    private ClassLoader classLoader;

    public AbstractApplicationContext(String configFile) {
        defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultBeanFactory);
        Resource resource = getResourceByPath(configFile);
        xmlBeanDefinitionReader.loadBeanDefinition(resource);
        defaultBeanFactory.setBeanClassLoader(this.getBeanClassLoader());
    }

    protected abstract Resource getResourceByPath(String configFile);

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return defaultBeanFactory.getBeanDefinition(beanId);
    }

    @Override
    public Object getBean(String beanId) {
        return defaultBeanFactory.getBean(beanId);
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader();
    }
}
