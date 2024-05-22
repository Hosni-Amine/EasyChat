package com.example.easychat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageButton;

import com.example.easychat.aircraft.AircraftFragment;
import com.example.easychat.report.ReportsFragment;
import com.example.easychat.user_chat.ChatFragment;
import com.example.easychat.user_chat.DashboardFragment;
import com.example.easychat.user_chat.ProfileFragment;
import com.example.easychat.user_chat.SearchUserActivity;
import com.example.easychat.utils.FirebaseUtil;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    ImageButton searchButton;
    ChatFragment chatFragment;
    DashboardFragment homeFragment;
    AircraftFragment searchAircraftFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatFragment = new ChatFragment();
        homeFragment = new DashboardFragment();
        searchAircraftFragment = new AircraftFragment();


        bottomNavigationView = findViewById(R.id.bottom_navigation);
        searchButton = findViewById(R.id.main_search_btn);
        searchButton.setOnClickListener((v)->{
            startActivity(new Intent(MainActivity.this, SearchUserActivity.class));
        });

        getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, homeFragment).commit();

            bottomNavigationView.setSelectedItemId(R.id.menu_home);

            bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    switch (item.getItemId()) {
                        case R.id.menu_chat:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout,chatFragment).commit();
                            break;
                        case R.id.menu_home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, homeFragment).commit();
                            break;
                        case R.id.menu_crafts:
                            getSupportFragmentManager().beginTransaction().replace(R.id.main_frame_layout, searchAircraftFragment).commit();
                            break;
                    }
                    return true;
                }
            });

        getFCMToken();
    }

    void getFCMToken(){
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                String token = task.getResult();
                FirebaseUtil.currentUserDetails().update("fcmToken",token);
            }
        });
    }
}
