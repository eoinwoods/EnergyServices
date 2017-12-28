package com.artechra.cpuhog;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestCpuhog {

    @Test
    public void testShortBurnTime() {
        Cpuhog hog = new Cpuhog(1, 10);

        long start = System.currentTimeMillis() ;
        hog.getContent() ;
        long end = System.currentTimeMillis() ;

        long diff = end - start ;
        assertTrue("Unexpected run time of " + diff + " msec",Math.abs(diff - 10) < 3);
    }
}
