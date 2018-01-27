package com.artechra.datahog;

import java.util.Base64;
import java.util.Random;
import java.util.logging.Logger;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

public class Datahog {

    static final String DEFAULT_MONGO_DB = "database" ;

    private final Logger LOG = Logger.getLogger(Datahog.class.getName());

    private Random rand = new Random() ;
    private final long id;
    private final int data_mb;
    private MongoClient mongoConn ;
    private String mongoDatabase ;

    public Datahog(long id, int data_mb, MongoClient mongoClient, String mongoDbName) {
        this.id = id;
        this.data_mb = data_mb;
        this.mongoConn = mongoClient ;
        this.mongoDatabase = mongoDbName ;
    }

    public Datahog(long id, int data_mb, MongoClient mongoClient) {
        this(id, data_mb, mongoClient, DEFAULT_MONGO_DB) ;
    }

    public long getId() {
        return id;
    }

    public String getContent() {
        LOG.info(String.format("Datahog called to store %d MB of data", this.data_mb));
        long startmsec = System.currentTimeMillis() ;
        storeData(this.data_mb);
        long endmsec = System.currentTimeMillis() ;
        String retval = String.format("Stored %d mb of data in %d msec", this.data_mb, (endmsec-startmsec));
        LOG.info("Completed Datahog process returning '" +
                retval + "')");
        return retval;
    }

    protected void storeData(int mbytes) {
        LOG.info(String.format("Storing %d 1024 byte blocks", mbytes));
        MongoOperations mongoOps = new MongoTemplate(this.mongoConn, this.mongoDatabase);
        for (int i = 1; i <= mbytes; i++) {
            DataItem dataItem = createDataItem(1024);
            mongoOps.insert(dataItem);
        }
    }

    protected DataItem createDataItem(int bytes) {
        StringBuilder dummyData = new StringBuilder() ;
        byte[] payloadBytes = allocateByteArray(bytes) ;
        String data = Base64.getEncoder().encodeToString(payloadBytes) ;
        dummyData.append(data) ;
        return new DataItem(rand.nextInt(), System.currentTimeMillis(), dummyData.toString()) ;
    }

    protected byte[] allocateByteArray(int sizeBytes) {
        byte[] ret = new byte[sizeBytes];
        Random rand = new Random();
        rand.nextBytes(ret);
        return ret;
    }

}
