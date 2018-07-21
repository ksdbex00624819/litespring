package com.like.zero.spring.core.io;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by like 2018/6/24
 */
public interface Resource {

    public InputStream getInputStream() throws IOException;

    public String getDescription();
}
