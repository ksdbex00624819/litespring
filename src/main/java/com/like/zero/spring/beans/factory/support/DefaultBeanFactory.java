package com.like.zero.spring.beans.factory.support;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.PropertyValue;
import com.like.zero.spring.beans.SimpleTypeConverter;
import com.like.zero.spring.beans.factory.BeanCreationException;
import com.like.zero.spring.beans.factory.BeanDefinitionStoreException;
import com.like.zero.spring.beans.factory.config.ConfigurableBeanFactory;
import com.like.zero.spring.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.util.List;
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
            throw new BeanCreationException("create bean for " + beanDefinition.getBeanClassName() + " failed", e);
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

    private Object createBean(BeanDefinition beanDefinition) {
        // 创建bean实例
        Object bean = instantiateBean(beanDefinition);
        // 设置bean的属性
        populateBean(beanDefinition, bean);

        return bean;
    }

    private void populateBean(BeanDefinition beanDefinition, Object bean) {
        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        if (propertyValues == null || propertyValues.isEmpty()) {
            return;
        }
        BeanDefinitionValueResolver resolver = new BeanDefinitionValueResolver(this);
        SimpleTypeConverter converter = new SimpleTypeConverter();
        try {
            for (PropertyValue propertyValue : propertyValues) {
                String propertyName = propertyValue.getName();
                Object resolvedValue = resolver.resolveValueIfNecessary(propertyValue.getValue());
                BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
                PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

                for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
                    // 属性名称一样，调用set方法
                    if (propertyDescriptor.getName().equals(propertyName)) {
                        Object convertedValue = converter.convertIfNecessary(resolvedValue, propertyDescriptor.getPropertyType());
                        propertyDescriptor.getWriteMethod().invoke(bean, convertedValue);
                        break;
                    }
                }
            }
        } catch (Exception ex) {
            throw new BeanCreationException("Failed to obtain BeanInfo for class [" + beanDefinition.getBeanClassName() + "]", ex);
        }
    }


    private Object instantiateBean(BeanDefinition beanDefinition) {
        ClassLoader classLoader = this.getBeanClassLoader();
        String beanClassName = beanDefinition.getBeanClassName();
        try {
            Class<?> clazz = classLoader.loadClass(beanDefinition.getBeanClassName());
            return clazz.newInstance();
        } catch (Exception e) {
            throw new BeanCreationException("create bean for " + beanClassName + " failed", e);
        }

    }
}
