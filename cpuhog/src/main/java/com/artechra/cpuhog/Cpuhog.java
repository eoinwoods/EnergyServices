package com.artechra.cpuhog;

import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Cpuhog {

    private final Logger LOG = Logger.getLogger(Cpuhog.class.getName());


    private final long id;
    private final int  time_msec;

    public Cpuhog(long id, int time_msec) {
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
            KeyGenerator kg = KeyGenerator.getInstance("AES") ;
            kg.init(128);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, kg.generateKey()) ;
            cipher.doFinal("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
        }
     } catch(Exception e) {
        LOG.warning("Exception while burning CPU: " + e);
        e.printStackTrace();
        throw new RuntimeException(e) ;
     }
    }
}