package com.like.zero.spring.core.io;

/**
 * Created by like
 * 2018/6/24
 */
public interface ResourceLoader {

    public Resource getResource(String location);

    public ClassLoader getClassLoader();
}
