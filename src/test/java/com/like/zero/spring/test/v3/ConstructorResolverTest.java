package com.like.zero.spring.test.v3;

import com.like.zero.spring.beans.BeanDefinition;
import com.like.zero.spring.beans.factory.support.ConstructorResolver;
import com.like.zero.spring.beans.factory.support.DefaultBeanFactory;
import com.like.zero.spring.beans.factory.xml.XmlBeanDefinitionReader;
import com.like.zero.spring.core.io.ClassPathResource;
import com.like.zero.spring.service.v3.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by like
 * 2018/7/21
 */
public class ConstructorResolverTest {

    @Test
    public void testAutoWireConstructor() {
        DefaultBeanFactory factory = new DefaultBeanFactory();
        XmlBeanDefinitionReader xmlReader = new XmlBeanDefinitionReader(factory);
        xmlReader.loadBeanDefinitions(new ClassPathResource("petstore-v3.xml"));

        BeanDefinition beanDefinition = factory.getBeanDefinition("petStore");

        ConstructorResolver constructorResolver = new ConstructorResolver(factory);
        PetStoreService petStoreService = (PetStoreService) constructorResolver.autowireConstructor(beanDefinition);

        // 验证函数version，正确地通过此构造函数做了初始化
        // PetStoreService(AccountDao accountDao, ItemDao itemDao, int version)
        Assert.assertEquals(1, petStoreService.getVersion());
        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
    }
}
