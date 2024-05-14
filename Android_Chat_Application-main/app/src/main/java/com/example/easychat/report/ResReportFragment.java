package com.example.easychat.report;

import android.app.DownloadManager;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.easychat.DashboardFragment;
import com.example.easychat.R;
import com.example.easychat.adapter.ChatRecyclerAdapter;
import com.example.easychat.adapter.RecentChatRecyclerAdapter;
import com.example.easychat.adapter.ReportRecyclerAdapter;
import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.model.ChatroomModel;
import com.example.easychat.model.ReportModel;
import com.example.easychat.model.UserModel;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class ResReportFragment extends Fragment {

    RecyclerView recyclerView;
    ReportRecyclerAdapter adapter;
    ImageButton backBtn;

    public ResReportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reports, container, false);
        recyclerView = view.findViewById(R.id.recyler_view_chat);

        ImageView addbutton = view.findViewById(R.id.add_report_btn);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddResReportFragment addReportFragment = new AddResReportFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frame_layout, addReportFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        backBtn = view.findViewById(R.id.back_btn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DashboardFragment dashboardFragment = new DashboardFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frame_layout, dashboardFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        setupRecyclerView();
        return view;
    }

    void setupRecyclerView() {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel currentUserModel = task.getResult().toObject(UserModel.class);
                String currentUserId = currentUserModel.getUserId();
                Query query = FirebaseUtil.getChatroomMessages()
                        .whereEqualTo("sender", currentUserId)
                        .orderBy("timestamp", Query.Direction.DESCENDING);
                if(currentUserModel.getUsertype().equals("Admin")) {
                    query = FirebaseUtil.getChatroomMessages()
                            .orderBy("timestamp", Query.Direction.DESCENDING);
                }
                FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                        .setQuery(query, ChatMessageModel.class)
                        .build();

                adapter = new ReportRecyclerAdapter(options, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                adapter.startListening();
            }
        });
    }




    @Override
    public void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        if(adapter!=null)
            adapter.stopListening();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter!=null)
            adapter.notifyDataSetChanged();
    }
}