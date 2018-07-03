package com.artechra.cpuhog;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

class Cpuhog {

    private static final long MILLI_TO_NANO_MULTIPLIER = 1_000_000;
    private final ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();
    private final Logger LOG = Logger.getLogger(Cpuhog.class.getName());
    private final boolean wallClockDuration;
    private Cipher cipher;
    private final long id ;
    private final int time_msec;

    Cpuhog(long id, int time_msec, boolean wallclockTime) {
        this.id = id ;
        this.time_msec = time_msec;
        this.wallClockDuration = wallclockTime;
        final KeyGenerator kg;
        try {
            kg = KeyGenerator.getInstance("AES");
            this.cipher = Cipher.getInstance("AES");
            this.cipher.init(Cipher.ENCRYPT_MODE, kg.generateKey());
        } catch (Exception e) {
            throw new IllegalStateException("Unable to initialise cryptographic system to burn CPU", e);
        }
        kg.init(128);

    }

    public String getContent() {
        LOG.info("Cpuhog " + this.id + " called for " + this.time_msec + " msec (" +
                (this.wallClockDuration ? "wallclock duration" : "CPU usage") + ")");
        long start = System.currentTimeMillis();
        long usageMsec;
        if (wallClockDuration) {
            usageMsec = burnTimeForMsec(this.time_msec);
        } else {
            usageMsec = burnMsecOfCpuTime(this.time_msec);
        }
        long end = System.currentTimeMillis();
        String ret = String.format("Burned %d msec CPU time in %d msec",
                usageMsec, (end - start));
        LOG.info("Cpuhog " + this.id + " returning: " + ret) ;
        return ret ;
    }

    private long burnMsecOfCpuTime(long msec) {

        long startUsageNanoSec = threadBean.getCurrentThreadCpuTime();
        while (threadBean.getCurrentThreadCpuTime() < startUsageNanoSec + (msec * MILLI_TO_NANO_MULTIPLIER)) {
            try {
                this.cipher.doFinal("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
            } catch (Exception e) {
                LOG.warning("Exception while burning CPU: " + e);
                throw new RuntimeException("Exception while burning CPU", e);
            }
        }
        long endUsageNanoSec = threadBean.getCurrentThreadCpuTime();
        return (endUsageNanoSec - startUsageNanoSec) / MILLI_TO_NANO_MULTIPLIER;
    }

    private long burnTimeForMsec(long msec) {
        long startTime = System.currentTimeMillis();
        long startUsageNanoSec = threadBean.getCurrentThreadCpuTime();
        try {
            while (System.currentTimeMillis() < startTime + msec) {
                this.cipher.doFinal("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
            }
        } catch (Exception e) {
            LOG.warning("Exception while burning CPU: " + e);
            throw new RuntimeException("Exception while burning CPU", e);
        }
        long endUsageNanoSec = threadBean.getCurrentThreadCpuTime();
        return (endUsageNanoSec - startUsageNanoSec) / MILLI_TO_NANO_MULTIPLIER;
    }
}
