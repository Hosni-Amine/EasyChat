package com.example.easychat.user_chat;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.easychat.CRMreports.CRMViewActivity;
import com.example.easychat.CRMreports.EditCRMReportActivity;
import com.example.easychat.R;
import com.example.easychat.SplashActivity;
import com.example.easychat.login.LoginUsernameActivity;
import com.example.easychat.model.UserModel;
import com.example.easychat.report.ReportsFragment;
import com.example.easychat.CRMreports.CRMReportFragment;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.internal.Slashes;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class DashboardFragment extends Fragment {
    private TextView dprofile_username, dprofile_email, dprofile_phone, dprofile_type;
    private ImageView profile_image;
    UserModel currentUserModel;
    ActivityResultLauncher<Intent> imagePickLauncher;
    Uri selectedImageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imagePickLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Intent data = result.getData();
                        if(data!=null && data.getData()!=null){
                            selectedImageUri = data.getData();
                            AndroidUtil.setProfilePic(getContext(),selectedImageUri,profile_image);
                        }
                    }
                }
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dashboard, container, false);

        RelativeLayout editProfileBtn = view.findViewById(R.id.edit_profile_btn);
        RelativeLayout rereports_btn = view.findViewById(R.id.rereports_btn);
        RelativeLayout reps_btn = view.findViewById(R.id.reps);
        RelativeLayout cathalog_btn = view.findViewById(R.id.cathalog);
        RelativeLayout logoutBtn = view.findViewById(R.id.logout_btn);
        dprofile_username = view.findViewById(R.id.dprofile_username);
        dprofile_email = view.findViewById(R.id.dprofile_email);
        dprofile_phone = view.findViewById(R.id.dprofile_phone);
        dprofile_type = view.findViewById(R.id.dprofile_type);
        profile_image = view.findViewById(R.id.profile_image);

        logoutBtn.setOnClickListener((v)->{
            FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseUtil.logout();
                        Intent intent = new Intent(getContext(), SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                    }
                }
            });
        });


        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileFragment profileFragment = new ProfileFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frame_layout, profileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        reps_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReportsFragment reportsFragment = new ReportsFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frame_layout, reportsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        rereports_btn
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRMReportFragment profileFragment = new CRMReportFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frame_layout, profileFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        cathalog_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://w3.airbus.com/H380/world/airbusworld/forms/airbus.sfcc?TYPE=33554433&REALMOID=06-653aefcb-c624-47d3-bc0a-85721530460f&GUID=&SMAUTHREASON=0&METHOD=GET&SMAGENTNAME=-SM-t%2fTX88CtTFIPqQ15ugBfOME66PQGCWGuAAOSByMZUiVIJPozo431pS4LE4YuSGzU&TARGET=-SM-HTTPS%3a%2f%2fw3%2eairbus%2ecom%2fnewairbusworld%2ffaces%2fHome%3f_afrLoop%3d5053020185423977%26_afrWindowMode%3d0%26_afrWindowId%3dnull%26_adf%2ectrl--state%3deuktpan70_1#!%40%40%3F_afrWindowId%3Dnull%26_afrLoop%3D5053020185423977%26_afrWindowMode%3D0%26_adf.ctrl-state%3Deuktpan70_5"; // Your URL here
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
            }
        });
        getUserData();
        return view;
    }

    void getUserData() {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                currentUserModel = task.getResult().toObject(UserModel.class);
                if (currentUserModel == null || currentUserModel.getUsername() == null || currentUserModel.getUsername().isEmpty() || currentUserModel.getEmail() == null || currentUserModel.getEmail().isEmpty()) {
                    AndroidUtil.showToast(getContext(), "Account registration was not finished");
                    FirebaseUtil.logout();
                    Intent intent = new Intent(getContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } else {
                    dprofile_username.setText(currentUserModel.getUsername());
                    dprofile_phone.setText(currentUserModel.getPhone());
                    dprofile_email.setText(currentUserModel.getEmail());
                    dprofile_type.setText("(" + currentUserModel.getUsertype() + ")");

                    FirebaseUtil.getCurrentProfilePicStorageRef().getDownloadUrl()
                            .addOnCompleteListener(picTask -> {
                                if (picTask.isSuccessful()) {
                                    Uri uri = picTask.getResult();
                                    AndroidUtil.setProfilePic(getContext(), uri, profile_image);
                                }
                            });
                }
            } else {
                AndroidUtil.showToast(getContext(), "Failed to retrieve user data");
            }
        });
    }

}
