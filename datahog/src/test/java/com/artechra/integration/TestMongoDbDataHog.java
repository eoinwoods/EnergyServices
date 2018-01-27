package com.artechra.integration;

import org.junit.Test;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import com.artechra.datahog.DataItem;
import com.artechra.datahog.Datahog;
import com.mongodb.MongoClient;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class TestMongoDbDataHog {

    static final String MONGO_DB = "database" ;
    @Test
    public void testBasicDataStoreOperationStoresData() {
        MongoClient mongoClient = new MongoClient() ;
        Datahog dh = new Datahog(1, 1, mongoClient, MONGO_DB) ;
        String ret = dh.getContent() ;
        assertNotNull(ret) ;
        MongoOperations mongoOps = new MongoTemplate(mongoClient, MONGO_DB);
        Query q = new Query();
        long count = mongoOps.count(q, MONGO_DB);
        assertEquals(1, count) ;
    }
}
