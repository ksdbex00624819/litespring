package com.like.zero.spring.core.io;

import com.like.zero.spring.util.ClassUtils;

/**
 * Created by like
 * 2018/6/24
 */
public class DefaultResourceLoader implements ResourceLoader {

    private ClassLoader classLoader;

    public DefaultResourceLoader() {
        this.classLoader = ClassUtils.getDefaultClassLoader();
    }

    public DefaultResourceLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public Resource getResource(String location) {
        if (location.substring(0, 2).matches("^[a-zA-z]:$")) {
            return new FileSystemResource(location);
        } else {
            return new ClassPathResource(location, getClassLoader());
        }
    }

    @Override
    public ClassLoader getClassLoader() {
        return (this.classLoader != null ? this.classLoader : ClassUtils.getDefaultClassLoader());
    }

    public void setClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }
}
