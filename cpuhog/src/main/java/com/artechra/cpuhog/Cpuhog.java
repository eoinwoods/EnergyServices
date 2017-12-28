package com.artechra.cpuhog;

import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;

public class Cpuhog {

    private final Logger LOG = Logger.getLogger(Cpuhog.class.getName());
    private KeyGenerator kg ;
    private Cipher cipher ;


    private final long id;
    private final int  time_msec;

    public Cpuhog(long id, int time_msec) {
        this.id = id;
        this.time_msec = time_msec;
        try {
            this.kg = KeyGenerator.getInstance("AES");
            this.cipher = Cipher.getInstance("AES");
            this.cipher.init(Cipher.ENCRYPT_MODE, kg.generateKey()) ;
        } catch(Exception e) {
            throw new IllegalStateException("Unable to initialise cryptographic system to burn CPU", e);
        }
        this.kg.init(128);

    }

    public long getId() {
        return id;
    }

    public String getContent() {
        long start = System.currentTimeMillis() ;
        burnTimeMsec(this.time_msec) ;
        long end = System.currentTimeMillis() ;
        String retval = String.format("Burned time for %d msec", (end - start)) ;
        return retval;
    }

    private void burnTimeMsec(long msec) {
      try {
        long startTime = System.currentTimeMillis() ;
        while(System.currentTimeMillis() < startTime + msec) {
            this.cipher.doFinal("ABCDEFGHIJKLMNOPQRSTUVWXYZ".getBytes());
        }
     } catch(Exception e) {
        LOG.warning("Exception while burning CPU: " + e);
        throw new RuntimeException("Exception while burning CPU", e) ;

     }
    }
}
