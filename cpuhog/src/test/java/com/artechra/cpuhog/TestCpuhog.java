package com.artechra.cpuhog;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestCpuhog {

    @Test
    public void testShortBurnTime() {
        runCpuhogForTime(1, 10, 2);
    }

    @Test
    public void testLongBurnTime() {
        runCpuhogForTime(2, 5 * 1000, 5);
    }

    private void runCpuhogForTime(int id, int timeMsec, int toleranceMsec) {
        Cpuhog hog = new Cpuhog(id,timeMsec);

        long start = System.currentTimeMillis() ;
        hog.getContent() ;
        long end = System.currentTimeMillis() ;

        long diff = end - start ;
        assertTrue("Unexpected run time of " + diff + " msec",Math.abs(diff - timeMsec) < toleranceMsec);
    }

}
