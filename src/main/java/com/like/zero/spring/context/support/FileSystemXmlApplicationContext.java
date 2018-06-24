package com.like.zero.spring.context.support;

import com.like.zero.spring.core.io.FileSystemResource;
import com.like.zero.spring.core.io.Resource;

/**
 * Created by like
 * 2018/6/24
 */
public class FileSystemXmlApplicationContext extends AbstractApplicationContext {

    public FileSystemXmlApplicationContext(String configFile) {
        super(configFile);
    }

    @Override
    protected Resource getResourceByPath(String configFile) {
        Resource resource = new FileSystemResource(configFile);
        return resource;
    }


}
