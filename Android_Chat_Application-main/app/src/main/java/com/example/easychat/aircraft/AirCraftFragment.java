package com.example.easychat.aircraft;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easychat.R;
import com.example.easychat.adapter.RecentChatRecyclerAdapter;
import com.example.easychat.model.Aircraft;
import com.example.easychat.model.ChatroomModel;
import com.example.easychat.model.UserModel;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.messaging.FirebaseMessaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class AirCraftFragment extends Fragment {

    ImageView airPic;
    String aircraftId;
    EditText serialNumber;
    EditText modelName;
    EditText manufacturer;
    TextView comment;
    Button updateProfileBtn;
    ProgressBar progressBar;
    String username;
    TextView productionDate;
    Aircraft currentAirModel;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    public AirCraftFragment() {

    }
    public AirCraftFragment(String aircraftId) {
        this.aircraftId=aircraftId;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            AndroidUtil.setAirPic(getContext(),selectedImageUri,airPic);
                        }
                    }
                });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_aircraft, container, false);
        serialNumber = view.findViewById(R.id.serial_name);
        modelName  = view.findViewById(R.id.model_name);
        updateProfileBtn = view.findViewById(R.id.details_btn);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        comment = view.findViewById(R.id.comment);
        productionDate = view.findViewById(R.id.date);

        updateProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateBtnClick();
            }
        });

        getUserData(this.aircraftId);
        return view;
    }

    void updateBtnClick(){
        String newComment = comment.getText().toString();
        if(newComment.isEmpty() || newComment.length()<3){
            comment.setError("Comment length should be at least 3 chars");
            return;
        }
        setInProgress(true);


        if(selectedImageUri!=null){
            FirebaseUtil.getCurrentAirPicStorageRef(this.aircraftId).putFile(selectedImageUri)
                    .addOnCompleteListener(task -> {
                        updateToFirestore();
                    });
        }else{
            updateToFirestore();
        }
    }

    void updateToFirestore(){
        getUserDetails();
        currentAirModel.setComment(comment.getText().toString());
        productionDate.setText(currentAirModel.getProductionDate());

        FirebaseUtil.currentAirDetails(this.aircraftId).set(currentAirModel)
                .addOnCompleteListener(task -> {
                    setInProgress(false);
                    if(task.isSuccessful()){
                        AndroidUtil.showToast(getContext(),"Updated successfully");
                    }else{
                        AndroidUtil.showToast(getContext(),"Updated failed");
                    }
                });
    }



    void getUserData(String id){
        setInProgress(true);

        FirebaseUtil.getAircraftReference(id).get().addOnCompleteListener(task -> {
            setInProgress(false);
            currentAirModel = task.getResult().toObject(Aircraft.class);
            manufacturer.setText(currentAirModel.getAircraftId());
            serialNumber.setText(currentAirModel.getSerialNumber());
            modelName.setText(currentAirModel.getModelName());
            comment.setText(currentAirModel.getComment());
            productionDate.setText(currentAirModel.getProductionDate());
            if(currentAirModel.getComment() != null && currentAirModel.getComment().length()>3) {
                comment.setText(currentAirModel.getComment());}
        });
    }


    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            updateProfileBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            updateProfileBtn.setVisibility(View.VISIBLE);
        }
    }
    public void getUserDetails() {
        // Set progress to true initially
        setInProgress(true);

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        username = document.getString("username");
                        Date currentDate = new Date();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
                        String formattedDate = sdf.format(currentDate);
                        currentAirModel.setProductionDate(username+" on "+formattedDate);
                    }
                } else {
                    // If the task is not successful, display a toast message indicating failure
                    AndroidUtil.showToast(getContext(), "Failed to retrieve user details");
                }

                // Set progress to false after completing the task
                setInProgress(false);
            }
        });
    }



}













