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
import android.widget.Spinner;
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


public class AddReportFragment extends Fragment {

    ImageButton backBtn;
    EditText text_input;
    Button add_Btn;
    ProgressBar progressBar;
    private TextView selectedDateTextView;
    private Button datePickerButton;
    private Calendar calendar;
    private Spinner userSpinner;
    private List<UserModel> userList = new ArrayList<>(); // Initialize userList here
    private List<String> userNames = new ArrayList<>(); // Initialize userNames here

    private ArrayAdapter<String> spinnerAdapter;
    Timestamp selectedDate;
    String reportId;
    String userId;

    public AddReportFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    private void fetchUserData() {
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel currentUserModel = task.getResult().toObject(UserModel.class);
                CollectionReference usersRef = FirebaseUtil.allUsersCollectionReference();
                usersRef.get().addOnCompleteListener(usersTask -> {
                    if (usersTask.isSuccessful()) {
                        userList.clear();
                        userNames.clear();
                        for (QueryDocumentSnapshot document : usersTask.getResult()) {
                            UserModel user = document.toObject(UserModel.class);
                            if(!(currentUserModel.getUserId().equals(user.getUserId()))) {
                                userList.add(user);
                                userNames.add(user.getUsername() + " (" + user.getPhone() + ")");
                            }
                        }
                        spinnerAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
        setInProgress(false);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_add_report, container, false);
        backBtn = view.findViewById(R.id.back_btn);
        add_Btn = view.findViewById(R.id.add_btn);
        text_input = view.findViewById(R.id.text_input);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        selectedDateTextView = view.findViewById(R.id.selected_date_text_view);
        datePickerButton = view.findViewById(R.id.date_picker_button);

        calendar = Calendar.getInstance();
        updateDateInView();

        userSpinner = view.findViewById(R.id.spinner);
        spinnerAdapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item, userNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(spinnerAdapter);

        fetchUserData();

        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                userId = userList.get(position).getUserId();
                reportId = FirebaseUtil.getChatroomId(FirebaseUtil.currentUserId(),userId);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }
        });

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
        sendMessageToUser(text);

        ReportsFragment reportsFragment = new ReportsFragment();
        FragmentManager fragmentManager = getParentFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.main_frame_layout, reportsFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    void sendMessageToUser(String message){
        setInProgress(true);
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            UserModel currentUserModel = task.getResult().toObject(UserModel.class);
                            ChatMessageModel chatMessageModel = new ChatMessageModel(message, currentUserModel.getUserId(), userId, selectedDate);
                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                            CollectionReference chatMessagesCollection = db.collection("reportselement");
                            chatMessagesCollection.add(chatMessageModel)
                                    .addOnSuccessListener(documentReference -> {
                                        text_input.setText("");
                                        AndroidUtil.showToast(getContext(), "Report created successfully");
                                    })
                                    .addOnFailureListener(e -> {
                                        AndroidUtil.showToast(getContext(), "Report creation failed");
                                    });
                        } else {
                            AndroidUtil.showToast(getContext(), "Failed to get current user details");
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













