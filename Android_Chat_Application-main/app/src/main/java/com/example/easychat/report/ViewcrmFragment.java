package com.example.easychat.report;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easychat.R;
import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.model.ChatroomModel;
import com.example.easychat.model.ReportModel;
import com.example.easychat.model.UserModel;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.UserDataReader;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;


public class ViewcrmFragment extends Fragment {

    ImageButton backBtn;
    ReportModel reportModel;
    Button add_Btn;
    ProgressBar progressBar;
    private Calendar calendar;
    EditText transitcheckname, flightnumber, stationFromInput, stationToInput, departureBBInput, departureABInput,
            captainAcceptanceInput, arrivalABInput, arrivalBBInput, totalBBInput, totalABInput,
            readingBeforeRefuelingInput, upliftKgInput, upliftLInput, readingAtDepartureInput,
            readingAtArrivalInput, actualDencityInput, upliftEng1Input, upliftEng2Input,
            readingAtDepartureEng1Input, readingAtDepartureEng2Input, readingAtArrivalEng1Input,
            readingAtArrivalEng2Input, inspectionCheckTypeInput, stampLicenceInput, stationInput,
            docRefDocInput,pirepsmareps,actiontaken,actype;
    Timestamp selectedDate;

    public ViewcrmFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_viewcrm, container, false);
        backBtn = view.findViewById(R.id.back_btn);
        progressBar = view.findViewById(R.id.profile_progress_bar);

        actype = view.findViewById(R.id.actype);
        transitcheckname = view.findViewById(R.id.transitcheckname);
        flightnumber = view.findViewById(R.id.flightnumber);
        stationFromInput = view.findViewById(R.id.stationf);
        stationToInput = view.findViewById(R.id.stationt);
        departureBBInput = view.findViewById(R.id.departurebb);
        departureABInput = view.findViewById(R.id.departureab);
        captainAcceptanceInput = view.findViewById(R.id.acceptancename);
        arrivalABInput = view.findViewById(R.id.arrivalAb);
        arrivalBBInput = view.findViewById(R.id.arrivalBb);
        totalBBInput = view.findViewById(R.id.totaltimebb);
        totalABInput = view.findViewById(R.id.totaletimeab);
        readingBeforeRefuelingInput = view.findViewById(R.id.readingbeforerefuel);
        upliftKgInput = view.findViewById(R.id.UpliftK);
        upliftLInput = view.findViewById(R.id.upliftL);
        readingAtDepartureInput = view.findViewById(R.id.readingatdeparture);
        readingAtArrivalInput = view.findViewById(R.id.readingafterarrival);
        actualDencityInput = view.findViewById(R.id.actualdencity);
        upliftEng1Input = view.findViewById(R.id.engine1uplift);
        upliftEng2Input = view.findViewById(R.id.engine2uplift);
        readingAtDepartureEng1Input = view.findViewById(R.id.engine1departure);
        readingAtDepartureEng2Input = view.findViewById(R.id.engine2departure);
        readingAtArrivalEng1Input = view.findViewById(R.id.engine1arrival);
        readingAtArrivalEng2Input = view.findViewById(R.id.engine1arrival);
        inspectionCheckTypeInput = view.findViewById(R.id.checktype);
        stampLicenceInput = view.findViewById(R.id.stemplicence);
        stationInput = view.findViewById(R.id.station);
        docRefDocInput = view.findViewById(R.id.docref);
        pirepsmareps = view.findViewById(R.id.pirepsmareps);
        actiontaken = view.findViewById(R.id.actiontaken);



        backBtn.setOnClickListener(new View.OnClickListener() {
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
        setInProgress(false);
        return view;
    }

    void setInProgress(boolean inProgress) {
        if (inProgress) {
            progressBar.setVisibility(View.VISIBLE);
            add_Btn.setVisibility(View.GONE);
        } else {
            progressBar.setVisibility(View.GONE);
            add_Btn.setVisibility(View.VISIBLE);
        }
    }
}













