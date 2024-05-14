package com.example.easychat.report;

import android.app.DatePickerDialog;
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


public class AddResReportFragment extends Fragment {

    ImageButton backBtn;
    EditText text_input;
    ReportModel reportModel;
    Button add_Btn;
    ProgressBar progressBar;
    private TextView selectedDateTextView;
    private Button datePickerButton;
    private Calendar calendar;
    private List<UserModel> userList = new ArrayList<>(); // Initialize userList here
    private List<String> userNames = new ArrayList<>(); // Initialize userNames here

    Timestamp selectedDate;
    String reportId;
    public AddResReportFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_res_report, container, false);
        backBtn = view.findViewById(R.id.back_btn);
        add_Btn = view.findViewById(R.id.add_btn);
        text_input = view.findViewById(R.id.text_input);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        selectedDateTextView = view.findViewById(R.id.selected_date_text_view);
        datePickerButton = view.findViewById(R.id.date_picker_button);
//
//        EditText preFlightCheckNameInput = view.findViewById(R.id.pre_flight_check_name_input);
//        EditText flightNumberInput = view.findViewById(R.id.flight_number_input);
//        EditText stationFromInput = view.findViewById(R.id.station_from_input);
//        EditText stationToInput = view.findViewById(R.id.station_to_input);
//        EditText departureBBInput = view.findViewById(R.id.departure_bb_input);
//        EditText departureABInput = view.findViewById(R.id.departure_ab_input);
//        EditText captainAcceptanceInput = view.findViewById(R.id.captain_acceptance_input);
//        EditText arrivalABInput = view.findViewById(R.id.arrival_ab_input);
//        EditText arrivalBBInput = view.findViewById(R.id.arrival_bb_input);
//        EditText totalBBInput = view.findViewById(R.id.total_bb_input);
//        EditText totalABInput = view.findViewById(R.id.total_ab_input);
//        EditText readingBeforeRefuelingInput = view.findViewById(R.id.reading_before_refueling_input);
//        EditText upliftKgInput = view.findViewById(R.id.uplift_kg_input);
//        EditText upliftLInput = view.findViewById(R.id.uplift_l_input);
//        EditText readingAtDepartureInput = view.findViewById(R.id.reading_at_departure_input);
//        EditText readingAtArrivalInput = view.findViewById(R.id.reading_at_arrival_input);
//        EditText actualDencityInput = view.findViewById(R.id.actual_density_input);
//        EditText upliftEng1Input = view.findViewById(R.id.uplift_eng1_input);
//        EditText upliftEng2Input = view.findViewById(R.id.uplift_eng2_input);
//        EditText readingAtDepartureEng1Input = view.findViewById(R.id.reading_at_departure_eng1_input);
//        EditText readingAtDepartureEng2Input = view.findViewById(R.id.reading_at_departure_eng2_input);
//        EditText readingAtArrivalEng1Input = view.findViewById(R.id.reading_at_arrival_eng1_input);
//        EditText readingAtArrivalEng2Input = view.findViewById(R.id.reading_at_arrival_eng2_input);
//        EditText inspectionCheckTypeInput = view.findViewById(R.id.inspection_check_type_input);
//        EditText stampLicenceInput = view.findViewById(R.id.stamp_licence_input);
//        EditText stationInput = view.findViewById(R.id.station_input);
//        EditText docRefDocInput = view.findViewById(R.id.doc_ref_doc_input);

        calendar = Calendar.getInstance();
        updateDateInView();


        datePickerButton.setOnClickListener(v -> {
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(requireContext(),
                    (view1, year1, monthOfYear, dayOfMonth1) -> {
                        calendar.set(Calendar.YEAR, year1);
                        calendar.set(Calendar.MONTH, monthOfYear);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth1);
                        Calendar selectedCalendar = Calendar.getInstance();
                        selectedCalendar.set(Calendar.YEAR, year1);
                        selectedCalendar.set(Calendar.MONTH, monthOfYear);
                        selectedCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth1);
                        selectedDate = new Timestamp(selectedCalendar.getTime());
                    }, year, month, dayOfMonth);

            datePickerDialog.show();
        });
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
        add_Btn.setOnClickListener((v -> {
            setAdd_Btn();
        }));
        setInProgress(false);
        return view;
    }

    void setAdd_Btn(){
        String text = text_input.getText().toString();
        if(text.isEmpty() || text.length()<3){
            text_input.setError("Text length should be at least 3 chars");
            return;
        }
        if (selectedDate == null) {
            AndroidUtil.showToast(getContext(), "Please select a date");
            return;
        }
        sendResReport();

        ReportsFragment reportsFragment = new ReportsFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame_layout, reportsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void sendResReport(){
        setInProgress(true);
        reportId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(),FirebaseUtil.currentUserId());
        FirebaseUtil.getReportReference(reportId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                reportModel = task.getResult().toObject(ReportModel.class);
                if(reportModel==null){
                    reportModel = new ReportModel(
                    );
                    FirebaseUtil.getReportReference(reportId).set(reportModel);
                }
            }
        });
        setInProgress(false);
    }


    void setInProgress(boolean inProgress){
        if(inProgress){
            progressBar.setVisibility(View.VISIBLE);
            add_Btn.setVisibility(View.GONE);
        }else{
            progressBar.setVisibility(View.GONE);
            add_Btn.setVisibility(View.VISIBLE);
        }
    }

    private void updateDateInView() {
        // Format the selected date and display it in the text view
        String selectedDate = String.format("%02d/%02d/%04d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR));
        selectedDateTextView.setText("Selected Date: "+selectedDate);
    }
}













