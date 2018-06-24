package com.like.zero.spring.core.io;

import com.like.zero.spring.util.Assert;

import java.io.*;

/**
 * Created by like
 * 2018/6/24
 */
public class FileSystemResource implements Resource {

    private String path;

    private File file;

    public FileSystemResource(String path) {
        Assert.notNull(path, "path must not be null");
        this.path = path;
        this.file = new File(path);
    }

    @Override
    public InputStream getInputStream() throws IOException {
        InputStream inputStream = new FileInputStream(file);
        if (inputStream == null) {
            throw new FileNotFoundException(path + "cannot be opened");
        }
        return inputStream;
    }

    @Override
    public String getDescription() {
        return "file [" + file.getAbsolutePath() + "]";
    }
}
