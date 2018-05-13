package com.artechra.fiohog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Logger;

public class Fiohog {

    private final int BYTES_FOR_2014_B64CHARS = 767 ;

    private final Logger LOG = Logger.getLogger(Fiohog.class.getName());

    private Random rand = new Random();
    private final long id;
    private final int data_mb;
    private final boolean deleteFileOnExit ;
    private File dataFile ;

    public Fiohog(long id, int data_mb, boolean deleteFileOnExit) {
        this.id = id;
        this.data_mb = data_mb;
        this.deleteFileOnExit = deleteFileOnExit ;
    }

    public Fiohog(long id, int data_mb) {
        this(id, data_mb, true) ;
    }

    private File createTempFile() throws IOException {
        File tempFile =  File.createTempFile("fiohog.", ".txt");
        if (this.deleteFileOnExit) {
            tempFile.deleteOnExit();
        }
        return tempFile ;
    }

    public long getId() {
        return id;
    }

    public String getContent() throws IOException {
        LOG.info(String.format("Fiohog called to store %d MB of data", this.data_mb));
        long startmsec = System.currentTimeMillis();
        this.dataFile = createTempFile() ;
        storeData(this.dataFile, this.data_mb);
        long endmsec = System.currentTimeMillis();
        String retval = String
                .format("Stored %d mb of data in %d msec in file %s", this.data_mb, (endmsec - startmsec), this.dataFile.getAbsolutePath());
        LOG.info("Completed Fiohog process returning '" +
                retval + "')");
        return retval;
    }

    public String getTemporaryFilePath() {
        return this.dataFile == null ? null : this.dataFile.getAbsolutePath() ;
    }

    private void storeData(File dataFile, int mbytes) throws IOException {
        int mbyteBlockCount = 1024 * mbytes ;
        LOG.info(String.format("Storing %d 1024 byte blocks", mbyteBlockCount));
        FileWriter fw = new FileWriter(dataFile);
        for (int i = 1; i <= mbyteBlockCount; i++) {
            DataItem dataItem = createDataItem(BYTES_FOR_2014_B64CHARS);
            fw.write(dataItem.getPayload());
        }
        fw.close();
    }

    protected DataItem createDataItem(int bytes) {
        StringBuilder dummyData = new StringBuilder();
        byte[] payloadBytes = allocateByteArray(bytes);
        String data = Base64.getEncoder().encodeToString(payloadBytes);
        dummyData.append(data);
        return new DataItem(rand.nextInt(), System.currentTimeMillis(), dummyData.toString());
    }

    protected byte[] allocateByteArray(int sizeBytes) {
        byte[] ret = new byte[sizeBytes];
        Random rand = new Random();
        rand.nextBytes(ret);
        return ret;
    }

}
