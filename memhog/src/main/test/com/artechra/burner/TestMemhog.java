package com.artechra.burner;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestMemhog {

    @Test
    public void testAllocationOfAByteArray() {
        Memhog mh = new Memhog(100, 100, 100) ;

        byte[] array = mh.allocateByteArray(100);
        assertEquals(array.length, 100) ;
        int sum = 0 ;
        for(Byte b : array) {
            sum += b ;
        }
        assertNotEquals(sum, 0);
    }
}