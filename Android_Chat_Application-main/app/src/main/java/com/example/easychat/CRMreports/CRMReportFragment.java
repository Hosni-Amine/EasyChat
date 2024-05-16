package com.example.easychat.CRMreports;

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

import com.example.easychat.user_chat.DashboardFragment;
import com.example.easychat.R;
import com.example.easychat.adapter.CRMReportRecyclerAdapter;
import com.example.easychat.model.ReportModel;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;

public class CRMReportFragment extends Fragment {

    RecyclerView recyclerView;
    CRMReportRecyclerAdapter adapter;
    ImageButton backBtn;

    public CRMReportFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_res_report, container, false);
        recyclerView = view.findViewById(R.id.recyler_view_chat);

        ImageView addbutton = view.findViewById(R.id.add_report_btn);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddCRMReportFragment addReportFragment = new AddCRMReportFragment();
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

                Query query = FirebaseUtil.getResReports()
                        .orderBy("timestamp", Query.Direction.DESCENDING);
                FirestoreRecyclerOptions<ReportModel> options = new FirestoreRecyclerOptions.Builder<ReportModel>()
                        .setQuery(query, ReportModel.class)
                        .build();

                adapter = new CRMReportRecyclerAdapter(options, getContext());
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                recyclerView.setAdapter(adapter);
                adapter.startListening();
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