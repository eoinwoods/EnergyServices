package com.artechra.cpuhog;

import java.util.Random;
import java.util.logging.Logger;

public class Memhog {

    private final Logger LOG = Logger.getLogger(Memhog.class.getName());

    private final long id;
    private final int mem_mb;
    private final int duration_msec;

    public Memhog(long id, int mem_mb, int duration_msec) {
        this.id = id;
        this.mem_mb = mem_mb;
        this.duration_msec = duration_msec;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        String retval = String.format("Hogged %d mb of memory for %d msec", this.mem_mb, this.duration_msec);
        LOG.info("Starting to hog memory for getContent()");
        hogMemory(this.mem_mb, this.duration_msec);
        LOG.info("Completed memhog process for getContent() --> '" +
                retval + "')");
        return retval;
    }

    protected void hogMemory(int mbytes, int msec) {
        LOG.info(String.format("Allocating %d 1024 byte byte-arrays", mbytes));
        byte[][] arrays = new byte[mbytes][1024];
        for (int item=0; item < mbytes; item++) {
            arrays[item] = allocateByteArray(1024) ;
        }
        try {
            Thread.sleep(msec);
        } catch (InterruptedException ie) {
            LOG.warning("Interruption while waiting: " + ie);
            arrays = null ;
            throw new RuntimeException(ie);
        } finally {
            arrays = null ;
        }

    }

    protected byte[] allocateByteArray(int sizeBytes) {
        byte[] ret = new byte[sizeBytes];
        Random rand = new Random();
        rand.nextBytes(ret);
        return ret;
    }
}
