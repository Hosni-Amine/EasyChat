package com.example.easychat.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easychat.CRMreports.CRMViewActivity;
import com.example.easychat.CRMreports.EditCRMReportActivity;
import com.example.easychat.MainActivity;
import com.example.easychat.R;
import com.example.easychat.SplashActivity;
import com.example.easychat.model.UserModel;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.messaging.FirebaseMessaging;

public class LoginUsernameActivity extends AppCompatActivity {

    TextView logoutBtn;
    EditText usernameInput;
    EditText emailInput;
    Button letMeInBtn;
    ProgressBar progressBar;
    String phoneNumber;
    String verificationcode;
    UserModel userModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_username);
        logoutBtn = findViewById(R.id.logout_btn);
        usernameInput = findViewById(R.id.login_username);
        emailInput = findViewById(R.id.login_email);
        letMeInBtn = findViewById(R.id.login_let_me_in_btn);
        progressBar =findViewById(R.id.login_progress_bar);
        setInProgress(false);
        phoneNumber = getIntent().getExtras().getString("phone");
        verificationcode = getIntent().getExtras().getString("otp");
        getUsername();

        letMeInBtn.setOnClickListener((v -> {
            setUsername();
        }));

        logoutBtn.setOnClickListener((v)->{
            FirebaseMessaging.getInstance().deleteToken().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        FirebaseUtil.logout();
                        Intent intent = new Intent(LoginUsernameActivity.this, SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });

        });
    }

    void setUsername(){
        String username = usernameInput.getText().toString();
        String email = emailInput.getText().toString();

        if(username.isEmpty() || username.length()<3){
            usernameInput.setError("Username length should be at least 3 chars");
            return;
        }
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailInput.setError("Invalid email format");
            return;
        }

        Query query = FirebaseUtil.allUserTypesCollectionReference()
                .whereEqualTo("otp",verificationcode);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                String userType = "Technician";
                if (task.isSuccessful()) {
                    QuerySnapshot querySnapshot = task.getResult();
                    if (!querySnapshot.isEmpty()) {
                        DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                        userType = documentSnapshot.getString("type");
                    }
                }
                userModel = new UserModel(phoneNumber,username,email,userType,Timestamp.now(),FirebaseUtil.currentUserId());
                FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        setInProgress(false);
                        if(task.isSuccessful()){
                            Intent intent = new Intent(LoginUsernameActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
    void getUsername(){
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if(task.isSuccessful()){
                  userModel = task.getResult().toObject(UserModel.class);
                 if(userModel!=null){
                     FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                         @Override
                         public void onComplete(@NonNull Task<Void> task) {
                             setInProgress(false);
                             if(task.isSuccessful()){
                                 Intent intent = new Intent(LoginUsernameActivity.this,MainActivity.class);
                                 intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                 startActivity(intent);
                             }
                             FirebaseUtil.currentUserDetails().set(userModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                                 @Override
                                 public void onComplete(@NonNull Task<Void> task) {
                                     setInProgress(false);
                                     if(task.isSuccessful()){
                                         Intent intent = new Intent(LoginUsernameActivity.this,MainActivity.class);
                                         intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                         startActivity(intent);
                                     }
                                 }
                             });
                         }
                     });
                 }
                }
            }
        });
    }
    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            letMeInBtn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            letMeInBtn.setVisibility(View.VISIBLE);
        }
    }

}