package com.like.zero.spring.v1;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.factory.BeanCreationException;
import com.like.zero.spring.beans.factory.BeanDefinitionStoreException;
import com.like.zero.spring.beans.factory.support.DefaultBeanFactory;
import com.like.zero.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.like.zero.spring.core.io.ClassPathResource;
import com.like.zero.spring.core.io.Resource;
import com.like.zero.spring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by like
 * 2018/6/9
 */
public class BeanFactoryTest {

    DefaultBeanFactory beanFactory = null;
    XmlBeanDefinitionReader xmlBeanDefinitionReader = null;

    @Before
    public void setUP() {
        beanFactory = new DefaultBeanFactory();
        xmlBeanDefinitionReader = new XmlBeanDefinitionReader(beanFactory);
    }

    @Test
    public void testGetBean() {
        Resource resource = new ClassPathResource("petstore-v1.xml");
        xmlBeanDefinitionReader.loadBeanDefinition(resource);
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("petStore");

        Assert.assertEquals("com.like.zero.spring.service.v1.PetStoreService", beanDefinition.getBeanClassName());

        PetStoreService petStore = (PetStoreService) beanFactory.getBean("petStore");

        Assert.assertNotNull(petStore);
    }

    @Test
    public void testInvalidBean() {
        Resource resource = new ClassPathResource("petstore-v1.xml");
        xmlBeanDefinitionReader.loadBeanDefinition(resource);

        try {
            beanFactory.getBean("invalidBean");
        } catch (BeanCreationException e) {
            return;
        }

        Assert.fail("expect BeanCreationException");
    }

    @Test
    public void testInvalidXML() {
        try {
            Resource resource = new ClassPathResource("xxx-v1.xml");
            xmlBeanDefinitionReader.loadBeanDefinition(resource);
        } catch (BeanDefinitionStoreException e) {
            return;
        }

        Assert.fail("expect BeanDefinitionStoreException");
    }
}
