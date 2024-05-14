package com.example.easychat.model;

import com.google.firebase.Timestamp;

public class Aircraft {

    private String aircraftId;
    private String modelName;
    private String manufacturer;
    private String productionDate;
    private String serialNumber;
    private String comment;

    public Aircraft() {
    }

    public Aircraft(String aircraftId, String modelName, String manufacturer, String productionDate, String serialNumber, String comment) {
        this.aircraftId = aircraftId;
        this.modelName = modelName;
        this.manufacturer = manufacturer;
        this.productionDate = productionDate;
        this.serialNumber = serialNumber;
        this.comment = comment; // Initialize comment in the constructor
    }

    public String getAircraftId() {
        return aircraftId;
    }

    public void setAircraftId(String aircraftId) {
        this.aircraftId = aircraftId;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
