package com.like.zero.spring;

import com.like.zero.spring.v1.V1AllTests;
import com.like.zero.spring.v2.V2AllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by like
 * 2018/7/1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTests.class, V2AllTests.class})
public class AllTests {
}
