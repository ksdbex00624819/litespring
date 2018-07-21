package com.like.zero.spring.test.v2;

import com.like.zero.spring.beans.SimpleTypeConverter;
import com.like.zero.spring.beans.TypeConverter;
import com.like.zero.spring.beans.TypeMismatchException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by like 2018/7/1
 */
public class TypeConverterTest {

    @Test
    public void testConverterStringToInt() {
        TypeConverter converter = new SimpleTypeConverter();
        Integer i = converter.convertIfNecessary("3", Integer.class);
        Assert.assertEquals(3, i.intValue());

        try {
            converter.convertIfNecessary("3.1", Integer.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }

    @Test
    public void testConverterStringToBoolean() {
        TypeConverter converter = new SimpleTypeConverter();
        Boolean i = converter.convertIfNecessary("true", Boolean.class);
        Assert.assertEquals(true, i.booleanValue());

        try {
            converter.convertIfNecessary("aabbcc", Boolean.class);
        } catch (TypeMismatchException e) {
            return;
        }
        Assert.fail();
    }
}
