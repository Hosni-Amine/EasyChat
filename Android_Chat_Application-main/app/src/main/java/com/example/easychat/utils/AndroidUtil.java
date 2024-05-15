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
    public static UserModel getUserModelFromIntent(Intent intent){
        UserModel userModel = new UserModel();
        userModel.setUsername(intent.getStringExtra("username"));
        userModel.setPhone(intent.getStringExtra("phone"));
        userModel.setUserId(intent.getStringExtra("userId"));
        userModel.setFcmToken(intent.getStringExtra("fcmToken"));
        return userModel;
    }


    public static void setProfilePic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
    public static void setAirPic(Context context, Uri imageUri, ImageView imageView){
        Glide.with(context).load(imageUri).apply(RequestOptions.circleCropTransform()).into(imageView);
    }
}
