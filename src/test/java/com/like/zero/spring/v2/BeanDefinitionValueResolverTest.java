package com.like.zero.spring.v2;

import com.like.zero.spring.beans.factory.config.RuntimeBeanReference;
import com.like.zero.spring.beans.factory.config.TypedStringValue;
import com.like.zero.spring.beans.factory.support.BeanDefinitionValueResolver;
import com.like.zero.spring.beans.factory.support.DefaultBeanFactory;
import com.like.zero.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.like.zero.spring.core.io.ClassPathResource;
import com.like.zero.spring.dao.v2.AccountDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by like
 * 2018/7/1
 */
public class BeanDefinitionValueResolverTest {

    BeanDefinitionValueResolver resolver;

    @Before
    public void prepareBeanDefinitionValueResolver(){
        DefaultBeanFactory defaultBeanFactory = new DefaultBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader = new XmlBeanDefinitionReader(defaultBeanFactory);
        xmlBeanDefinitionReader.loadBeanDefinition(new ClassPathResource("petstore-v2.xml"));
        resolver = new BeanDefinitionValueResolver(defaultBeanFactory);
    }

    @Test
    public void testResolveRuntimeBeanReference() {
        RuntimeBeanReference reference = new RuntimeBeanReference("accountDao");
        Object value = resolver.resolveValueIfNecessary(reference);

        Assert.assertNotNull(value);
        Assert.assertTrue(value instanceof AccountDao);
    }

    @Test
    public void testTypedStringValue() {
        TypedStringValue typedStringValue = new TypedStringValue("test");
        Object value = resolver.resolveValueIfNecessary(typedStringValue);

        Assert.assertNotNull(value);
        Assert.assertEquals("test", value);
    }
}
