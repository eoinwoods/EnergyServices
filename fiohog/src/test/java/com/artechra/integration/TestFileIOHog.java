package com.artechra.integration;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;

public class TestFileIOHog {

    private File workingFile;

    @Before
    public void initialise() throws IOException {
        this.workingFile = File.createTempFile("Fiohog", "txt");
    }

    @Test
    public void testBasicDataStoreOperationStoresData() {
        assertFalse(true);
    }
}
