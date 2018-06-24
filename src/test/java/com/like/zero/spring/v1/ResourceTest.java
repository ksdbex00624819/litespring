package com.like.zero.spring.v1;

import com.like.zero.spring.core.io.ClassPathResource;
import com.like.zero.spring.core.io.FileSystemResource;
import com.like.zero.spring.core.io.Resource;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.InputStream;

/**
 * Created by like
 * 2018/6/24
 */
public class ResourceTest {

    Resource resource = null;

    @After
    public void validateInputStream() throws Exception {
        InputStream is = null;

        try {
            is = resource.getInputStream();
            // TODO 测试不充分
            Assert.assertNotNull(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    @Test
    public void testClassPathResource() throws Exception {
        resource = new ClassPathResource("petstore-v1.xml");

    }

    @Test
    public void testFileSystemResource() throws Exception {
        resource = new FileSystemResource("F:\\icode\\zero-spring\\litespring\\src\\test\\resources\\petstore-v1.xml");
    }
}
