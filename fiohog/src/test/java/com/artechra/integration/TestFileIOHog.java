package com.artechra.integration;

import java.io.File;
import java.io.IOException;

import com.artechra.fiohog.Fiohog;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class TestFileIOHog {

    private File workingFile;

    @Test
    public void testBasicDataStoreOperationStoresData() throws IOException {
        final int BYTE_LEN_OF_100MB = 1024 * 1024 * 100 ;
        // Note we don't delete file on exit for debugging so something needs to clear them up
        Fiohog fiohog = new Fiohog(1, 100, false);
        fiohog.getContent() ;
        File tempFile = new File(fiohog.getTemporaryFilePath()) ;
        assertTrue("Can't read temporary file from Fiohog", tempFile.exists() && tempFile.canRead()) ;
        assertEquals("Unexpected temporary file length", BYTE_LEN_OF_100MB, tempFile.length()) ;
    }
}
