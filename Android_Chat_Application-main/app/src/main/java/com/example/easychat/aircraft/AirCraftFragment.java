package com.example.easychat.aircraft;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.easychat.R;
import com.example.easychat.adapter.RecentChatAircraftRecyclerAdapter;
import com.example.easychat.utils.FirebaseUtil;
import com.example.easychat.model.Aircraft;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.Query;


public class AircraftFragment extends Fragment {


        RecyclerView recyclerView;
        RecentChatAircraftRecyclerAdapter adapter;

        public AircraftFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_search_aircraft, container, false);
            recyclerView = view.findViewById(R.id.recyler_view);
            setupRecyclerView();
            return view;
        }

        void setupRecyclerView() {
            Query query = FirebaseUtil.allAircraftCollectionReference();

            FirestoreRecyclerOptions<Aircraft> options = new FirestoreRecyclerOptions.Builder<Aircraft>()
                    .setQuery(query, Aircraft.class).build();

            adapter = new RecentChatAircraftRecyclerAdapter(options, getContext());
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
