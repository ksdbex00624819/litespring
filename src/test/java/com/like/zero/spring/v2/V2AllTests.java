package com.like.zero.spring.v2;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by like
 * 2018/7/1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({ApplicationContextTestV2.class, BeanDefinitionTestV2.class, BeanDefinitionValueResolverTest.class,
        CustomBooleanEditorTest.class, CustomNumberEditorTest.class, TypeConverterTest.class})
public class V2AllTests {
}
