package com.artechra.cpuhog;

import java.util.logging.Logger;
import java.util.logging.Level ;

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
        // A log statement in the getContent() method can add 10msec of
        // execution time and so needs switched off to avoid messing with
        // the test timings
        Logger cpuhogLogger = Logger.getLogger(Cpuhog.class.getName()) ;
        cpuhogLogger.setLevel(Level.SEVERE);

        Cpuhog hog = new Cpuhog(id,timeMsec);

        long start = System.currentTimeMillis() ;
        hog.getContent() ;
        long end = System.currentTimeMillis() ;

        long diff = end - start ;
        assertTrue("Unexpected run time of " + diff + " msec",Math.abs(diff - timeMsec) < toleranceMsec);
    }

}
