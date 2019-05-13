package com.ez08.trade;

import android.content.Context;
import android.net.Uri;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Iterator;
import java.util.Set;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        String args = "http://123.com?FUN=410501&TBL_OUT=custid,regflag,bondreg,opendate,market,secuid,name,fundid,secuseq,status;109000512,0,0,20070801,0,0001262540,闻之梅,0,0,0;109000512,1,0,20070801,1,A337929351,闻之梅,0,0,0;109000512,0,0,20070810,2,2091163681,闻之梅,0,0,0;109000512,1,0,20070801,3,C103869886,闻之梅,0,0,0;109000512,1,0,20141230,5,A337929351,闻之梅,0,0,0;109000512,0,0,20180418,6,0001262540,闻之梅,0,0,0;109000512,0,0,20180420,S,0001262540,闻之梅,0,0,0;";

        Uri uri = Uri.parse(args);
        Set<String> pn = uri.getQueryParameterNames();

        for (Iterator it = pn.iterator(); it.hasNext(); ) {
            System.out.println("value=" + it.next().toString());
        }

    }
}
