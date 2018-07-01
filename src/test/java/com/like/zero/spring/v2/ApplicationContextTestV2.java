package com.like.zero.spring.v2;

import com.like.zero.spring.context.ApplicationContext;
import com.like.zero.spring.context.support.ClassPathXmlApplicationContext;
import com.like.zero.spring.dao.v2.AccountDao;
import com.like.zero.spring.dao.v2.ItemDao;
import com.like.zero.spring.service.v2.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by like
 * 2018/6/30
 */
public class ApplicationContextTestV2 {

    @Test
    public void testGetBeanProperty() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v2.xml");
        PetStoreService petStoreService = (PetStoreService) applicationContext.getBean("petStore");

        Assert.assertNotNull(petStoreService.getAccountDao());
        Assert.assertNotNull(petStoreService.getItemDao());
        Assert.assertNotNull(petStoreService.getOwner());

        Assert.assertTrue(petStoreService.getAccountDao() instanceof AccountDao);
        Assert.assertTrue(petStoreService.getItemDao() instanceof ItemDao);
        Assert.assertEquals("like", petStoreService.getOwner());
        Assert.assertEquals(2, petStoreService.getVersion());
    }
}
