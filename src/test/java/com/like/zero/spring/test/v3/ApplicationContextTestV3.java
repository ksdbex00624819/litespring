package com.like.zero.spring.test.v3;

import com.like.zero.spring.context.ApplicationContext;
import com.like.zero.spring.context.support.ClassPathXmlApplicationContext;
import com.like.zero.spring.service.v3.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by like 2018/7/21
 */
public class ApplicationContextTestV3 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v3.xml");
        PetStoreService petStoreService = (PetStoreService) applicationContext.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertEquals(1, petStoreService.getVersion());
    }
}
