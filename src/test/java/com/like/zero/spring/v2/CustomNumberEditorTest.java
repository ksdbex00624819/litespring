package com.like.zero.spring.v2;

import com.like.zero.spring.beans.propertyeditors.CustomNumberEditor;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by like
 * 2018/7/1
 */
public class CustomNumberEditorTest {

    @Test
    public void testConverterString() {
        CustomNumberEditor editor = new CustomNumberEditor(Integer.class, true);

        editor.setAsText("3");
        Object value = editor.getValue();
        Assert.assertTrue(value instanceof Integer);
        Assert.assertEquals(3, ((Integer) editor.getValue()).intValue());

        editor.setAsText("");
        Assert.assertTrue(editor.getValue() == null);

        try {
            editor.setAsText("3.1");
        } catch (IllegalArgumentException e) {
            return;
        }
        Assert.fail();
    }
}
