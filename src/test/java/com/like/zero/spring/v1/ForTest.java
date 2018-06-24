package com.like.zero.spring.v1;

import org.junit.Test;

/**
 * Created by like
 * 2018/6/24
 */
public class ForTest {

    @Test
    public void test() {

        long sum = 0;

        for (int i = 1; i <= 10; i++) {
            long temp = 1;
            for (int j = 1; j <= i; j++) {
                temp = temp * j;
            }

            sum = sum + temp;
        }

        System.out.println(sum);


    }

    @Test
    public void test1() {
        int size = 6;
        if (size % 2 == 0) {
            size++; // 计算菱形大小
        }
        for (int i = 0; i < size / 2 + 1; i++) {
//            for (int j = size / 2 + 1; j > i + 1; j--) {
//                System.out.print(" "); // 输出左上角位置的空白
//            }
            for (int j = 0; j < 2 * i + 1; j++) {
                System.out.print("*"); // 输出菱形上半部边缘
            }
            System.out.println(); // 换行
        }
//        for (int i = size / 2 + 1; i < size; i++) {
//            for (int j = 0; j < i - size / 2; j++) {
//                System.out.print(" "); // 输出菱形左下角空白
//            }
//            for (int j = 0; j < 2 * size - 1 - 2 * i; j++) {
//                System.out.print("*"); // 输出菱形下半部边缘
//            }
//            System.out.println(); // 换行
//        }
    }
}


