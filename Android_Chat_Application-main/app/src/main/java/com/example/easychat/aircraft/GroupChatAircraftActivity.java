package com.example.easychat.aircraft;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easychat.R;
import com.example.easychat.adapter.RecentChatAircraftRecyclerAdapter;
import com.example.easychat.model.Aircraft;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class GroupChatAircraftActivity extends AppCompatActivity {

    EditText searchInput;
    ImageButton searchButton;
    ImageButton backButton;
    RecyclerView recyclerView;
    RecentChatAircraftRecyclerAdapter adapter;
    Query initialQuery;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_aircraft);
        searchInput = findViewById(R.id.seach_airname_input);
        searchButton = findViewById(R.id.search_air_btn);
        backButton = findViewById(R.id.back_btn);
        recyclerView = findViewById(R.id.search_air_recycler_view);
        searchInput.requestFocus();

        initialQuery = FirebaseUtil.allAircraftCollectionReference();

        backButton.setOnClickListener(v -> onBackPressed());

        setupInitialRecyclerView();
    }

    void setupInitialRecyclerView() {
        FirestoreRecyclerOptions<Aircraft> options = new FirestoreRecyclerOptions.Builder<Aircraft>()
                .setQuery(initialQuery, Aircraft.class).build();

        adapter = new RecentChatAircraftRecyclerAdapter(options, getApplicationContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.startListening();

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (adapter != null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (adapter != null)
            adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (adapter != null)
            adapter.startListening();
    }
}
