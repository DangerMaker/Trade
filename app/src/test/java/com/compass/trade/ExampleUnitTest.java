package com.compass.trade;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test(){
        String aaa = "15261051.82";
        String bbb = "14987854.22";

        double r = Double.parseDouble(aaa) - Double.parseDouble(bbb);
        System.out.println(r);

    }

}