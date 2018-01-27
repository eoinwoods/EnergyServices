package com.artechra.datahog;

/**
 * Created by eoin on 21/10/2017.
 */
public class DataItem {
    private int id ;
    private String payload ;


    public DataItem(final int id, String payload) {
        this.id = id;
        this.payload = payload;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getPayload() {
        return payload;
    }

    public void setPayload(final String payload) {
        this.payload = payload;
    }

    @Override public String toString() {
        return "DataItem{" +
                "id='" + id + '\'' +
                ", payload='" + payload + '\'' +
                '}';
    }
}
