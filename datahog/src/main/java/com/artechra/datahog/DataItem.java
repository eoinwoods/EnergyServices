package com.artechra.datahog;

/**
 * Created by eoin on 21/10/2017.
 */
public class DataItem {
    private int id ;
    private long timestamp ;
    private String payload ;

    /**
     * Construct a Data Item to take space in a MongoDB database
     * @param id a caller generated ID which is intended to be unique but this is not enforced
     * @param timestamp a caller generated millisecond timestamp for the record (probably System.currentTimeMillis())
     * @param payload the data to write to take up space
     */
    public DataItem(final int id, long timestamp, String payload) {
        this.id = id;
        this.timestamp = timestamp ;
        this.payload = payload;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final long timestamp) {
        this.timestamp = timestamp;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(final String payload) {
        this.payload = payload;
    }

    @Override public String toString() {
        return "DataItem{" +
                "id='" + id + "', " +
                "timestamp='" + timestamp + "', " +
                ", payload='" + payload + "'" +
                "}";
    }
}
