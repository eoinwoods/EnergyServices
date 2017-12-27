package com.artechra.datahog;

import java.util.Base64;
import java.util.Random;
import java.util.logging.Logger;

import com.mongodb.MongoClient;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Datahog {

    private final Logger LOG = Logger.getLogger(Datahog.class.getName());

    private Random rand = new Random() ;
    private final long id;
    private final int data_mb;

    public Datahog(long id, int data_mb) {
        this.id = id;
        this.data_mb = data_mb;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        LOG.info("Starting to store data for getContent()");
        long startmsec = System.currentTimeMillis() ;
        storeData(this.data_mb);
        long endmsec = System.currentTimeMillis() ;
        String retval = String.format("Stored %d mb of data in %d msec", this.data_mb, (endmsec-startmsec));
        LOG.info("Completed memhog process for getContent() --> '" +
                retval + "')");
        return retval;
    }

    protected void storeData(int mbytes) {
        DataItem dataItem = getDataToStore(mbytes);
        LOG.info(String.format("Storing %d 1024 byte blocks", mbytes));
        MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "database");
        mongoOps.insert(dataItem);
    }

    protected DataItem getDataToStore(int mbytes) {
        StringBuilder dummyData = new StringBuilder() ;
        for (int item=0; item < mbytes; item++) {
            byte[] oneMegaByte = allocateByteArray(1024) ;
            String data = Base64.getEncoder().encodeToString(oneMegaByte) ;
            dummyData.append(data) ;
        }
        return new DataItem(rand.nextInt(), dummyData.toString()) ;
    }

    protected byte[] allocateByteArray(int sizeBytes) {
        byte[] ret = new byte[sizeBytes];
        Random rand = new Random();
        rand.nextBytes(ret);
        return ret;
    }

}
