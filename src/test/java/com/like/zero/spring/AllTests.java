package com.like.zero.spring;

import com.like.zero.spring.test.v1.V1AllTests;
import com.like.zero.spring.test.v2.V2AllTests;
import com.like.zero.spring.test.v3.V3AllTests;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Created by like 2018/7/1
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({V1AllTests.class, V2AllTests.class, V3AllTests.class})
public class AllTests {

}
