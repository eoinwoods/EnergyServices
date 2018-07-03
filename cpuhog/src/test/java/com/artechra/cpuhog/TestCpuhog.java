package com.artechra.cpuhog;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.logging.Logger;
import java.util.logging.Level;

import org.junit.Test;

import static junit.framework.TestCase.assertTrue;

public class TestCpuhog {

    @Test
    public void testShortBurnTime() {
        runCpuhogForElapsedTime(1, 10, 2);
    }

    @Test
    public void testLongBurnTime() {
        runCpuhogForElapsedTime(2, 5 * 1000, 5);
    }

    @Test
    public void testLargeUsageTarget() {
        final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        final int targetUsage = 5 * 1000 ; // 5 seconds
        Cpuhog hog = new Cpuhog(3, targetUsage, false);

        long startUsageNanos = threadBean.getCurrentThreadCpuTime() ;
        hog.getContent();
        long endUsageNanos = threadBean.getCurrentThreadCpuTime() ;

        long usageMsec = (endUsageNanos - startUsageNanos) / 1_000_000 ;
        assertTrue("Unexpected usage value of " + usageMsec,
                Math.abs(usageMsec - targetUsage) < 2) ;

    }

    private void runCpuhogForElapsedTime(int id, int timeMsec, int toleranceMsec) {
        // A log statement in the getContent() method can add 10msec of
        // execution time and so needs switched off to avoid messing with
        // the test timings
        Logger cpuhogLogger = Logger.getLogger(Cpuhog.class.getName());
        cpuhogLogger.setLevel(Level.SEVERE);

        Cpuhog hog = new Cpuhog(id, timeMsec, true);

        long start = System.currentTimeMillis();
        hog.getContent();
        long end = System.currentTimeMillis();

        long diff = end - start;
        assertTrue("Unexpected run time of " + diff + " msec", Math.abs(diff - timeMsec) < toleranceMsec);
    }

}
