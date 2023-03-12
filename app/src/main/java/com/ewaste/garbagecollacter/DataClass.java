package com.ewaste.garbagecollacter;

public class DataClass {
    private String dataName;
    private String dataImage;
    private String dataWeight;
    private String dataAdd;

    private String dataNumber;
    private String dataClint;
    private String key;

    public void setKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public String getDataName() {
        return dataName;
    }

    public String getDataImage() {
        return dataImage;
    }

    public String getDataWeight() {
        return dataWeight;
    }

    public String getDataAdd() {
        return dataAdd;
    }

    public String getDataNumber() {
        return dataNumber;
    }

    public String getDataClint() {
        return dataClint;
    }

    public DataClass(String dataName, String dataImage, String dataWeight, String dataAdd, String dataNumber, String dataClint) {
        this.dataName = dataName;
        this.dataImage = dataImage;
        this.dataWeight = dataWeight;
        this.dataAdd = dataAdd;
        this.dataNumber = dataNumber;
        this.dataClint = dataClint;
    }
    public DataClass(){

    }
}
