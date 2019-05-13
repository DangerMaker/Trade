package com.ez08.trade;

import android.net.Uri;

import com.ez08.trade.tools.CommonUtils;

import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test() {
        String args = "FUN=410501&TBL_OUT=custid,regflag,bondreg,opendate,market,secuid,name,fundid,secuseq,status;109000512,0,0,20070801,0,0001262540,闻之梅,0,0,0;109000512,1,0,20070801,1,A337929351,闻之梅,0,0,0;109000512,0,0,20070810,2,2091163681,闻之梅,0,0,0;109000512,1,0,20070801,3,C103869886,闻之梅,0,0,0;109000512,1,0,20141230,5,A337929351,闻之梅,0,0,0;109000512,0,0,20180418,6,0001262540,闻之梅,0,0,0;109000512,0,0,20180420,S,0001262540,闻之梅,0,0,0;";

        String a = CommonUtils.deleteAllCRLF(args);
        System.out.print(a);
    }

}