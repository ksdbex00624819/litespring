package com.like.zero.spring.test.v2;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.PropertyValue;
import com.like.zero.spring.beans.factory.config.RuntimeBeanReference;
import com.like.zero.spring.beans.factory.support.DefaultBeanFactory;
import com.like.zero.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.like.zero.spring.core.io.ClassPathResource;
import com.like.zero.spring.core.io.Resource;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by like 2018/6/30
 */
public class BeanDefinitionTestV2 {

    @Test
    public void testGetBeanDefinition() {
        Resource resource = new ClassPathResource("petstore-v2.xml");
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(
                defaultBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinitions(resource);
        BeanDefinition beanDefinition = defaultBeanFactory.getBeanDefinition("petStore");

        List<PropertyValue> propertyValues = beanDefinition.getPropertyValues();
        Assert.assertTrue(propertyValues.size() == 4);
        {
            PropertyValue propertyValue = getPropertyValue("accountDao", propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }
        {
            PropertyValue propertyValue = getPropertyValue("itemDao", propertyValues);
            Assert.assertNotNull(propertyValue);
            Assert.assertTrue(propertyValue.getValue() instanceof RuntimeBeanReference);
        }


    }

    private PropertyValue getPropertyValue(final String propertyName,
                                           List<PropertyValue> propertyValues) {
        List<PropertyValue> filterPropertyValues = propertyValues.stream()
                .filter(property -> property.getName().equals(propertyName)).collect(Collectors.toList());
        if (filterPropertyValues != null && !filterPropertyValues.isEmpty()) {
            return filterPropertyValues.get(0);
        }
        return null;
    }
}
