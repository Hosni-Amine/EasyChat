package com.example.easychat.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.easychat.MainActivity;
import com.example.easychat.R;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.hbb20.CountryCodePicker;

public class LoginActivity extends AppCompatActivity {

    CountryCodePicker countryCodePicker;
    EditText passwordInput;
    EditText phoneInput;
    Button letMeInBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        phoneInput = findViewById(R.id.login_mobile);
        passwordInput = findViewById(R.id.login_password);
        letMeInBtn = findViewById(R.id.sub_login_btn);
        progressBar =findViewById(R.id.login_progress_bar);

        phoneInput.setText(getIntent().getExtras().getString("phone"));




        letMeInBtn.setOnClickListener((v -> {
            setInProgress(true);
            Query query = FirebaseUtil.allUserCollectionReference()
                    .whereEqualTo("phone",getIntent().getExtras().getString("phone"));
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        QuerySnapshot querySnapshot = task.getResult();
                        if (!querySnapshot.isEmpty()) {
                            DocumentSnapshot documentSnapshot = querySnapshot.getDocuments().get(0);
                            String storedPassword = documentSnapshot.getString("password");
                            String enteredPassword = passwordInput.getText().toString();
                            if (storedPassword != null && enteredPassword.equals(storedPassword)) {
                                setInProgress(false);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK );
                                startActivity(intent);
                            } else {
                                setInProgress(false);
                                AndroidUtil.showToast(getApplicationContext(), "Password invalid");
                            }
                        }
                    }
                    else {
                        setInProgress(false);
                        AndroidUtil.showToast(getApplicationContext(), "Phone invalid");
                    }
                }
                });
            }));

        setInProgress(false);

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