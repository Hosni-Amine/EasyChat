package com.example.easychat.model;

import com.google.firebase.Timestamp;

import java.util.List;

public class ReportModel {
    String reportId;
    private String preFlightCheckName;
    private String flightNumber;
    private String stationFrom;
    private String stationTo;
    private String departureBB;
    private String departureAB;
    private String captainAcceptance;
    private String arrivalAB;
    private String arrivalBB;
    private String totalBB;
    private String totalAB;
    private String readingBeforeRefueling;
    private String upliftKg;
    private String upliftL;
    private String readingAtDeparture;
    private String readingAtArrival;
    private String actualDencity;
    private String upliftEng1;
    private String upliftEng2;
    private String readingAtDepartureEng1;
    private String readingAtDepartureEng2;
    private String readingAtArrivalEng1;
    private String readingAtArrivalEng2;
    private String inspectionCheckType;
    private String stampLicence;
    private String station;
    private String docRefDoc;
    private Timestamp docRefTimeWithHours;
    private String pirepsMareps;
    private String actionTaken;
    private String senderId;
    private Timestamp timestamp;

    public ReportModel() {
    }

    public ReportModel(String reportId, String preFlightCheckName, String flightNumber, String stationFrom, String stationTo, String departureBB, String departureAB, String captainAcceptance, String arrivalAB, String arrivalBB, String totalBB, String totalAB, String readingBeforeRefueling, String upliftKg, String upliftL, String readingAtDeparture, String readingAtArrival, String actualDencity, String upliftEng1, String upliftEng2, String readingAtDepartureEng1, String readingAtDepartureEng2, String readingAtArrivalEng1, String readingAtArrivalEng2, String inspectionCheckType, String stampLicence, String station, String docRefDoc, Timestamp docRefTimeWithHours, String pirepsMareps, String actionTaken, String senderId, Timestamp timestamp) {
        this.reportId = reportId;
        this.preFlightCheckName = preFlightCheckName;
        this.flightNumber = flightNumber;
        this.stationFrom = stationFrom;
        this.stationTo = stationTo;
        this.departureBB = departureBB;
        this.departureAB = departureAB;
        this.captainAcceptance = captainAcceptance;
        this.arrivalAB = arrivalAB;
        this.arrivalBB = arrivalBB;
        this.totalBB = totalBB;
        this.totalAB = totalAB;
        this.readingBeforeRefueling = readingBeforeRefueling;
        this.upliftKg = upliftKg;
        this.upliftL = upliftL;
        this.readingAtDeparture = readingAtDeparture;
        this.readingAtArrival = readingAtArrival;
        this.actualDencity = actualDencity;
        this.upliftEng1 = upliftEng1;
        this.upliftEng2 = upliftEng2;
        this.readingAtDepartureEng1 = readingAtDepartureEng1;
        this.readingAtDepartureEng2 = readingAtDepartureEng2;
        this.readingAtArrivalEng1 = readingAtArrivalEng1;
        this.readingAtArrivalEng2 = readingAtArrivalEng2;
        this.inspectionCheckType = inspectionCheckType;
        this.stampLicence = stampLicence;
        this.station = station;
        this.docRefDoc = docRefDoc;
        this.docRefTimeWithHours = docRefTimeWithHours;
        this.pirepsMareps = pirepsMareps;
        this.actionTaken = actionTaken;
        this.senderId = senderId;
        this.timestamp = timestamp;
    }


    // Getters
    public String getReportId() {
        return reportId;
    }

    public String getPreFlightCheckName() {
        return preFlightCheckName;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public String getStationFrom() {
        return stationFrom;
    }

    public String getStationTo() {
        return stationTo;
    }

    public String getDepartureBB() {
        return departureBB;
    }

    public String getDepartureAB() {
        return departureAB;
    }

    public String getCaptainAcceptance() {
        return captainAcceptance;
    }

    public String getArrivalAB() {
        return arrivalAB;
    }

    public String getArrivalBB() {
        return arrivalBB;
    }

    public String getTotalBB() {
        return totalBB;
    }

    public String getTotalAB() {
        return totalAB;
    }

    public String getReadingBeforeRefueling() {
        return readingBeforeRefueling;
    }

    public String getUpliftKg() {
        return upliftKg;
    }

    public String getUpliftL() {
        return upliftL;
    }

    public String getReadingAtDeparture() {
        return readingAtDeparture;
    }

    public String getReadingAtArrival() {
        return readingAtArrival;
    }

    public String getActualDencity() {
        return actualDencity;
    }

    public String getUpliftEng1() {
        return upliftEng1;
    }

    public String getUpliftEng2() {
        return upliftEng2;
    }

    public String getReadingAtDepartureEng1() {
        return readingAtDepartureEng1;
    }

    public String getReadingAtDepartureEng2() {
        return readingAtDepartureEng2;
    }

    public String getReadingAtArrivalEng1() {
        return readingAtArrivalEng1;
    }

    public String getReadingAtArrivalEng2() {
        return readingAtArrivalEng2;
    }

    public String getInspectionCheckType() {
        return inspectionCheckType;
    }

    public String getStampLicence() {
        return stampLicence;
    }

    public String getStation() {
        return station;
    }

    public String getDocRefDoc() {
        return docRefDoc;
    }

    public Timestamp getDocRefTimeWithHours() {
        return docRefTimeWithHours;
    }

    public String getPirepsMareps() {
        return pirepsMareps;
    }

    public String getActionTaken() {
        return actionTaken;
    }

    public String getSenderId() {
        return senderId;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }
}
