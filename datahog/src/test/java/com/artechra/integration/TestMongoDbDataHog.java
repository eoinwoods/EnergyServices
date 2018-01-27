package com.artechra.integration;

import java.util.Base64;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.artechra.datahog.DataItem;
import com.artechra.datahog.Datahog;
import com.mongodb.MongoClient;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TestMongoDbDataHog {

    static final String MONGO_DB = "database" ;
    MongoClient mongoClient ;
    MongoOperations mongoOps ;

    @Before
    public void initialise() {
        mongoClient = new MongoClient() ;
        mongoOps    = new MongoTemplate(mongoClient, MONGO_DB);
        mongoOps.dropCollection(DataItem.class);
    }

    @Test
    public void testBasicDataStoreOperationStoresData() {

        Datahog dh = new Datahog(1, 1, mongoClient, MONGO_DB) ;
        String ret = dh.getContent() ;
        assertNotNull(ret) ;

        long count = mongoOps.findAll(DataItem.class).size();
        assertEquals(1, count) ;
    }

    @Test
    public void testMultipleInsertResultsInExpectedNumberOfItems() {
        Datahog dh = new Datahog(1, 10, mongoClient, MONGO_DB) ;
        String ret = dh.getContent() ;
        assertNotNull(ret) ;

        List<DataItem> items = mongoOps.findAll(DataItem.class) ;
        assertEquals(10, items.size()) ;
        for (DataItem i : items) {
            assertEquals(1024, Base64.getDecoder().decode(i.getPayload()).length) ;
        }

    }
}
