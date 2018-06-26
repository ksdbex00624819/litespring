package com.like.zero.spring.beans.factory.support;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.factory.BeanCreationException;
import com.like.zero.spring.beans.factory.BeanDefinitionStoreException;
import com.like.zero.spring.beans.factory.config.ConfigurableBeanFactory;
import com.like.zero.spring.util.ClassUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by like
 * 2018/6/9
 */
public class DefaultBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory, BeanDefinitionRegistry {

    private ClassLoader classLoader;

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public DefaultBeanFactory() {
    }

    @Override
    public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) throws BeanDefinitionStoreException {
        this.beanDefinitionMap.put(beanName, beanDefinition);
    }

    @Override
    public BeanDefinition getBeanDefinition(String beanId) {
        return this.beanDefinitionMap.get(beanId);
    }

    @Override
    public Object getBean(String beanId) {
        BeanDefinition beanDefinition = getBeanDefinition(beanId);
        if (beanDefinition == null) {
            throw new BeanCreationException("Bean Definition does not exist");
        }
        try {
            if (beanDefinition.isSingleton()) {
                Object bean = getSingleton(beanId);
                if (getSingleton(beanId) == null) {
                    bean = createBean(beanDefinition);
                    registerSingleton(beanId, bean);
                }
                return bean;
            }
            return createBean(beanDefinition);
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanDefinition.getBeanClassName());
        }

    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public ClassLoader getBeanClassLoader() {
        return this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader();
    }

    private Object createBean(BeanDefinition beanDefinition){
        ClassLoader classLoader = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = classLoader.loadClass(beanDefinition.getBeanClassName());
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for "+ beanClassName +" failed",e);
        }

    }
}
