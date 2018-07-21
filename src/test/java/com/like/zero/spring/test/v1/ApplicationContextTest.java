package com.like.zero.spring.test.v1;

import com.like.zero.spring.context.ApplicationContext;
import com.like.zero.spring.context.support.ClassPathXmlApplicationContext;
import com.like.zero.spring.context.support.FileSystemXmlApplicationContext;
import com.like.zero.spring.service.v1.PetStoreService;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by like 2018/6/24
 */
public class ApplicationContextTest {

    @Test
    public void testGetBean() {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) applicationContext.getBean("petStore");
        Assert.assertNotNull(petStore);
    }

    @Test
    public void testGetBeanFromFileSystemContext() {
        ApplicationContext applicationContext = new FileSystemXmlApplicationContext(
                "F:\\icode\\zero-spring\\litespring\\src\\test\\resources\\petstore-v1.xml");
        PetStoreService petStore = (PetStoreService) applicationContext.getBean("petStore");
        Assert.assertNotNull(petStore);
    }
}
