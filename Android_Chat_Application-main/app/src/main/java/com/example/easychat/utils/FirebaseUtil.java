package com.example.easychat.utils;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.SimpleDateFormat;
import java.util.List;

public class FirebaseUtil {

    public static String currentUserId(){
        return FirebaseAuth.getInstance().getUid();
    }
    public static boolean isLoggedIn(){
        if(currentUserId()!=null){
            return true;
        }
        return false;
    }
    public static CollectionReference allAircraftCollectionReference() {
        return FirebaseFirestore.getInstance().collection("aircrafts");
    }

    public static DocumentReference currentUserDetails(){
        return FirebaseFirestore.getInstance().collection("users").document(currentUserId());
    }
    public static DocumentReference crmreportreference(String id){
        return FirebaseFirestore.getInstance().collection("reports").document(id);
    }
    public static DocumentReference otheruserUserDetails(String otheruser){
        return FirebaseFirestore.getInstance().collection("users").document(otheruser);
    }
    public static CollectionReference allUserCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static CollectionReference allUserTypesCollectionReference(){
        return FirebaseFirestore.getInstance().collection("usertypes");
    }
    public static DocumentReference getChatroomReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("chatrooms").document(chatroomId);
    }
    public static DocumentReference getReportReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("reports").document(chatroomId);
    }
    public static DocumentReference getReportelementReference(String chatroomId){
        return FirebaseFirestore.getInstance().collection("reportselement").document(chatroomId);
    }
    public static DocumentReference getGchatroomReference(String gchatroomId){
        return FirebaseFirestore.getInstance().collection("gchatrooms").document(gchatroomId);
    }

    public static CollectionReference getChatroomMessageReference(String chatroomId){
        return getChatroomReference(chatroomId).collection("chats");
    }
    public static CollectionReference getReports(){
        return FirebaseFirestore.getInstance().collection("reportselement");
    }
    public static CollectionReference getResReports(){
        return FirebaseFirestore.getInstance().collection("reports");
    }
    public static CollectionReference getGchatroomMessageReference(String gchatroomId){
        return getGchatroomReference(gchatroomId).collection("chats");
    }
    public static String getChatroomId(String userId1,String userId2){
        if(userId1.hashCode()<userId2.hashCode()){
            return userId1+"_"+userId2;
        }else{
            return userId2+"_"+userId1;
        }
    }
    public static CollectionReference allChatroomCollectionReference(){
        return FirebaseFirestore.getInstance().collection("chatrooms");
    }
    public static CollectionReference allReportsCollectionReference(){
        return FirebaseFirestore.getInstance().collection("reports");
    }
    public static CollectionReference allUsersCollectionReference(){
        return FirebaseFirestore.getInstance().collection("users");
    }
    public static DocumentReference getOtherUserFromChatroom(List<String> userIds){
        if(userIds.get(0).equals(FirebaseUtil.currentUserId())){
            return allUserCollectionReference().document(userIds.get(1));
        }else{
            return allUserCollectionReference().document(userIds.get(0));
        }
    }
    public static String timestampToString(Timestamp timestamp){
        return new SimpleDateFormat("HH:MM").format(timestamp.toDate());
    }
    public static String datestampToString(Timestamp timestamp){
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(timestamp.toDate());
    }
    public static void logout(){
        FirebaseAuth.getInstance().signOut();
    }
    public static StorageReference  getCurrentProfilePicStorageRef(){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(FirebaseUtil.currentUserId());
    }
    public static StorageReference  getOtherProfilePicStorageRef(String otherUserId){
        return FirebaseStorage.getInstance().getReference().child("profile_pic")
                .child(otherUserId);
    }
    public static StorageReference  getOtherAirPicStorageRef(String otherAirId){
        return FirebaseStorage.getInstance().getReference().child("air_pic")
                .child(otherAirId);
    }

}










