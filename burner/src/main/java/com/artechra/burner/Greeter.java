package com.artechra.burner;

import java.util.logging.Logger;


public class Greeter {

    private final Logger LOGGER = Logger.getLogger(Greeter.class.getName());


    private final long id;
    private final String content;

    public Greeter(long id, String content) {
        this.id = id;
        this.content = content;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
	     LOGGER.info("Starting burn process for getContent()"); 
	     burnTimeMsec(1000) ;
	     LOGGER.info("Completed burn process for getContent() --> " + 
                         content + ")");
       return content;
    }

    private void burnTimeMsec(long msec) {
      try {
        long startTime = System.currentTimeMillis() ;
        while(System.currentTimeMillis() < startTime + msec) {
           Thread.sleep(100);
        }
     } catch(InterruptedException ie) {
        LOGGER.warning("Interruption while waiting: " + ie);
        throw new RuntimeException(ie) ;
     }
    }
}
