package com.example.easychat.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.easychat.model.Aircraft;
import com.example.easychat.model.ReportModel;
import com.example.easychat.model.UserModel;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AndroidUtil {

   public static  void showToast(Context context,String message){
       Toast.makeText(context,message,Toast.LENGTH_LONG).show();
    }
    public static void passAirModelAsIntent(Intent intent, Aircraft model){
        intent.putExtra("modelName",model.getModelName());
        intent.putExtra("serialNumber",model.getSerialNumber());
        intent.putExtra("aircraftId",model.getAircraftId());
        intent.putExtra("manufacturer",model.getManufacturer());
        intent.putExtra("comment",model.getComment());
    }
    public static Aircraft getAirModelFromIntent(Intent intent){
        Aircraft airCraft = new Aircraft();
        airCraft.setModelName(intent.getStringExtra("modelName"));
        airCraft.setAircraftId(intent.getStringExtra("aircraftId"));
        airCraft.setManufacturer(intent.getStringExtra("manufacturer"));
        airCraft.setModelName(intent.getStringExtra("serialNumber"));
        airCraft.setComment(intent.getStringExtra("comment"));
        return airCraft;
    }
    public static void passUserModelAsIntent(Intent intent, UserModel model){
        intent.putExtra("username",model.getUsername());
        intent.putExtra("phone",model.getPhone());
        intent.putExtra("userId",model.getUserId());
        intent.putExtra("fcmToken",model.getFcmToken());
    }
    public static void passReportModelAsIntent(Intent intent, ReportModel model) {
        intent.putExtra("actypeText", model.getActypeText());
        intent.putExtra("reportId", model.getReportId());
        intent.putExtra("preFlightCheckName", model.getPreFlightCheckName());
        intent.putExtra("flightNumber", model.getFlightNumber());
        intent.putExtra("stationFrom", model.getStationFrom());
        intent.putExtra("stationTo", model.getStationTo());
        intent.putExtra("departureBB", model.getDepartureBB());
        intent.putExtra("departureAB", model.getDepartureAB());
        intent.putExtra("captainAcceptance", model.getCaptainAcceptance());
        intent.putExtra("arrivalAB", model.getArrivalAB());
        intent.putExtra("arrivalBB", model.getArrivalBB());
        intent.putExtra("totalBB", model.getTotalBB());
        intent.putExtra("totalAB", model.getTotalAB());
        intent.putExtra("readingBeforeRefueling", model.getReadingBeforeRefueling());
        intent.putExtra("upliftKg", model.getUpliftKg());
        intent.putExtra("upliftL", model.getUpliftL());
        intent.putExtra("readingAtDeparture", model.getReadingAtDeparture());
        intent.putExtra("readingAtArrival", model.getReadingAtArrival());
        intent.putExtra("actualDencity", model.getActualDencity());
        intent.putExtra("upliftEng1", model.getUpliftEng1());
        intent.putExtra("upliftEng2", model.getUpliftEng2());
        intent.putExtra("readingAtDepartureEng1", model.getReadingAtDepartureEng1());
        intent.putExtra("readingAtDepartureEng2", model.getReadingAtDepartureEng2());
        intent.putExtra("readingAtArrivalEng1", model.getReadingAtArrivalEng1());
        intent.putExtra("readingAtArrivalEng2", model.getReadingAtArrivalEng2());
        intent.putExtra("inspectionCheckType", model.getInspectionCheckType());
        intent.putExtra("stampLicence", model.getStampLicence());
        intent.putExtra("station", model.getStation());
        intent.putExtra("docRefDoc", model.getDocRefDoc());
        intent.putExtra("pirepsMareps", model.getPirepsMareps());
        intent.putExtra("actionTaken", model.getActionTaken());
        intent.putExtra("senderId", model.getSenderId());
        intent.putExtra("timestamp", model.getTimestamp());
    }
    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }
    public static ReportModel getReportModelFromIntent(Intent intent) {
        ReportModel reportModel = new ReportModel(
                intent.getStringExtra("reportId"),
                intent.getStringExtra("actypeText"),
                intent.getStringExtra("preFlightCheckName"),
                intent.getStringExtra("flightNumber"),
                intent.getStringExtra("stationFrom"),
                intent.getStringExtra("stationTo"),
                intent.getStringExtra("departureBB"),
                intent.getStringExtra("departureAB"),
                intent.getStringExtra("captainAcceptance"),
                intent.getStringExtra("arrivalAB"),
                intent.getStringExtra("arrivalBB"),
                intent.getStringExtra("totalBB"),
                intent.getStringExtra("totalAB"),
                intent.getStringExtra("readingBeforeRefueling"),
                intent.getStringExtra("upliftKg"),
                intent.getStringExtra("upliftL"),
                intent.getStringExtra("readingAtDeparture"),
                intent.getStringExtra("readingAtArrival"),
                intent.getStringExtra("actualDencity"),
                intent.getStringExtra("upliftEng1"),
                intent.getStringExtra("upliftEng2"),
                intent.getStringExtra("readingAtDepartureEng1"),
                intent.getStringExtra("readingAtDepartureEng2"),
                intent.getStringExtra("readingAtArrivalEng1"),
                intent.getStringExtra("readingAtArrivalEng2"),
                intent.getStringExtra("inspectionCheckType"),
                intent.getStringExtra("stampLicence"),
                intent.getStringExtra("station"),
                intent.getStringExtra("docRefDoc"),
                intent.getStringExtra("pirepsMareps"),
                intent.getStringExtra("actionTaken"),
                intent.getStringExtra("senderId"),
                intent.getStringExtra("timestamp")
        );
        return reportModel;
    }
    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
