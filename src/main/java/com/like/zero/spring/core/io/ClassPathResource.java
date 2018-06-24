package com.like.zero.spring.core.io;

import com.like.zero.spring.util.ClassUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by like
 * 2018/6/24
 */
public class ClassPathResource implements Resource {

    private String path;

    private ClassLoader classLoader;

    public ClassPathResource(String path) {
        this(path, null);
    }

    public ClassPathResource(String path, ClassLoader classLoader) {
        this.path = path;
        this.classLoader = classLoader == null ? ClassUtils.getDefaultClassLoader() : classLoader;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = this.classLoader.getResourceAsStream(this.path);
        if (inputStream == null) {
            throw new FileNotFoundException(path + "cannot be opened");
        }
        return inputStream;
    }

    @Override
    public String getDescription() {
        return this.path;
    }
}
