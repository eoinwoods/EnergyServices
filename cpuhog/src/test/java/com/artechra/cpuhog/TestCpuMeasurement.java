package com.artechra.cpuhog;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

import org.junit.Before;
import org.junit.Test;

public class TestCpuMeasurement {

    private ThreadMXBean threadBean;
    private Cipher cipher;
    private KeyGenerator kg;

    @Before
    public void getMBean() {
        threadBean = ManagementFactory.getThreadMXBean();

        try {
            this.kg = KeyGenerator.getInstance("AES");
            this.cipher = Cipher.getInstance("AES");
            this.cipher.init(Cipher.ENCRYPT_MODE, kg.generateKey());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to initialise cryptographic system to burn CPU", e);
        }
        this.kg.init(128);
    }

    private long nanoSecToMilliSec(long nanosec) {
        return nanosec / 1000000;
    }

    @Test
    public void testMeasuringCpuUsageForWallClockPeriod() throws Exception {
        Thread t = Thread.currentThread();

        long startThreadUsage = threadBean.getCurrentThreadCpuTime();
        long startTimeMsec = System.currentTimeMillis();

        while (System.currentTimeMillis() < startTimeMsec + 1000) {
            this.cipher.doFinal("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
        }

        long endTimeMsec = System.currentTimeMillis();
        long endThreadUsage = threadBean.getCurrentThreadCpuTime();

        System.out.println(String.format("Thread %d st=%d et=%d su=%d eu=%d", t.getId(),
                startTimeMsec, endTimeMsec,
                nanoSecToMilliSec(startThreadUsage), nanoSecToMilliSec(endThreadUsage)));
        System.out.println(String.format("Thread %d consumed %s CPU msec in %d msec",
                t.getId(), nanoSecToMilliSec(endThreadUsage - startThreadUsage),
                endTimeMsec - startTimeMsec));
    }

    @Test
    public void testMeasuringCpuUsageForUsageAmount() throws Exception {
        Thread t = Thread.currentThread();

        long startThreadUsage = threadBean.getCurrentThreadCpuTime();
        long startTimeMsec = System.currentTimeMillis();

        while (threadBean.getCurrentThreadCpuTime() < startThreadUsage + (1000 * 1_000_000)) {
            this.cipher.doFinal("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
        }

        long endTimeMsec = System.currentTimeMillis();
        long endThreadUsage = threadBean.getCurrentThreadCpuTime();

        System.out.println(String.format("Thread %d st=%d et=%d su=%d eu=%d", t.getId(),
                startTimeMsec, endTimeMsec,
                nanoSecToMilliSec(startThreadUsage), nanoSecToMilliSec(endThreadUsage)));
        System.out.println(String.format("Thread %d consumed %s CPU msec in %d msec",
                t.getId(), nanoSecToMilliSec(endThreadUsage - startThreadUsage),
                endTimeMsec - startTimeMsec));
    }

}
