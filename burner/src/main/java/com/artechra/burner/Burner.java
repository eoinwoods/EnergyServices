package com.artechra.burner;

import java.util.logging.Logger;


public class Burner {

    private final Logger LOG = Logger.getLogger(Burner.class.getName());


    private final long id;
    private final int  time_msec;

    public Burner(long id, int time_msec) {
        this.id = id;
        this.time_msec = time_msec;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        String retval = String.format("Burned time for %d msec", time_msec) ;
	     LOG.info("Starting burn process for getContent()");
	     burnTimeMsec(this.time_msec) ;
	     LOG.info("Completed burn process for getContent() --> '" +
                         retval + "')");
       return retval;
    }

    private void burnTimeMsec(long msec) {
      try {
        long startTime = System.currentTimeMillis() ;
        while(System.currentTimeMillis() < startTime + msec) {
           Thread.sleep(100);
        }
     } catch(InterruptedException ie) {
        LOG.warning("Interruption while waiting: " + ie);
        throw new RuntimeException(ie) ;
     }
    }
}
