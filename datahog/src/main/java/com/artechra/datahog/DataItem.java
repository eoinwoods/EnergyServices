package com.artechra.datahog;

/**
 * Created by eoin on 21/10/2017.
 */
public class DataItem {
    private int id ;
    private String dummyData ;


    public DataItem(final int id, String dummyData) {
        this.id = id;
        this.dummyData = dummyData;
    }

    public int getCount() {
        return 99;
    }

    public void setCount() {

    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getDummyData() {
        return dummyData;
    }

    public void setDummyData(final String dummyData) {
        this.dummyData = dummyData;
    }

    @Override public String toString() {
        return "DataItem{" +
                "id='" + id + '\'' +
                ", dummyData='" + dummyData + '\'' +
                '}';
    }
}
