package com.artechra.fiohog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Base64;
import java.util.Random;
import java.util.logging.Logger;

import com.artechra.fiohog.DataItem;

public class Fiohog {

    private final Logger LOG = Logger.getLogger(Fiohog.class.getName());

    private Random rand = new Random();
    private final long id;
    private final int data_mb;

    public Fiohog(long id, int data_mb) {
        this.id = id;
        this.data_mb = data_mb;
    }

    private File createTempFile() throws IOException {
        return File.createTempFile("fiohog", "txt");
    }

    public long getId() {
        return id;
    }

    public String getContent() throws IOException {
        LOG.info(String.format("Fiohog called to store %d MB of data", this.data_mb));
        File dataFile = createTempFile();
        long startmsec = System.currentTimeMillis();
        String tmpFileName = storeData(dataFile, this.data_mb);
        long endmsec = System.currentTimeMillis();
        String retval = String
                .format("Stored %d mb of data in %d msec in file %s", this.data_mb, (endmsec - startmsec), tmpFileName);
        LOG.info("Completed Fiohog process returning '" +
                retval + "')");
        return retval;
    }

    private String storeData(File dataFile, int mbytes) throws IOException {
        LOG.info(String.format("Storing %d 1024 byte blocks", mbytes));
        FileWriter fw = new FileWriter(dataFile);
        for (int i = 1; i <= mbytes; i++) {
            DataItem dataItem = createDataItem(1024);
            fw.write(dataItem.getPayload());
        }
        fw.close();
        return dataFile.getAbsolutePath();
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
