package com.artechra.datahog;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.mongodb.MongoClient;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

public class TestDatahog {

    MongoClient mongoClient = new MongoClient() ;

    @Test
    public void testCreateDataItemReturnsCredibleItem() {
        DataItem di = new Datahog(1, 1, mongoClient).createDataItem(1024) ;
        assertNotNull(di) ;
        assertTrue(di.getId() != 0) ;
        assertNotNull(di.getPayload()); ;
        assertEquals(1024, Base64.getDecoder().decode(di.getPayload()).length) ;
    }

    @Test
    public void testAllocateByteArrayAllocatesCredibleArray(){
        byte[] array = new Datahog(1, 1, mongoClient).allocateByteArray(1000) ;
        assertEquals(1000, array.length) ;
        List<Byte> items = new ArrayList<Byte>();
        for (byte b : array) {
            items.add(b) ;
        }
        Map<Byte, Long> result =
                items.stream().collect(
                        Collectors.groupingBy(
                                Function.identity(), Collectors.counting()
                        )
                );
        assertTrue(result.keySet().size() > 100) ;
    }
}
