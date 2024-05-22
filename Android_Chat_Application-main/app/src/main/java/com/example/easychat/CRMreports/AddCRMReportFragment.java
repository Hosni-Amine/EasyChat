package com.example.easychat.CRMreports;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.easychat.R;
import com.example.easychat.model.ReportModel;
import com.example.easychat.report.ReportsFragment;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;


public class AddCRMReportFragment extends Fragment {

    ImageButton backBtn;
    Button add_Btn;
    ProgressBar progressBar;
    private TextView selectedDateTextView;
    private Button datePickerButton;
    private Calendar calendar;
    EditText transitcheckname, flightnumber, stationFromInput, stationToInput, departureBBInput, departureABInput,
            captainAcceptanceInput, arrivalABInput, arrivalBBInput, totalBBInput, totalABInput,
            readingBeforeRefuelingInput, upliftKgInput, upliftLInput, readingAtDepartureInput,
            readingAtArrivalInput, actualDencityInput, upliftEng1Input, upliftEng2Input,
            readingAtDepartureEng1Input, readingAtDepartureEng2Input, readingAtArrivalEng1Input,
            readingAtArrivalEng2Input, inspectionCheckTypeInput, stampLicenceInput, stationInput,
            docRefDocInput,pirepsmareps,actiontaken,actype;
    Timestamp selectedDate;

    public AddCRMReportFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_res_report, container, false);
        backBtn = view.findViewById(R.id.back_btn);
        add_Btn = view.findViewById(R.id.add_btn);
        progressBar = view.findViewById(R.id.profile_progress_bar);
        selectedDateTextView = view.findViewById(R.id.selected_date_text_view);
        datePickerButton = view.findViewById(R.id.date_picker_button);

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

                        TimePickerDialog timePickerDialog = new TimePickerDialog(requireContext(),
                                (view2, hourOfDay, minute) -> {
                                    calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                    calendar.set(Calendar.MINUTE, minute);
                                    Calendar selectedCalendar = Calendar.getInstance();
                                    selectedCalendar.set(year1, monthOfYear, dayOfMonth1, hourOfDay, minute);
                                    selectedDate = new Timestamp(selectedCalendar.getTime());
                                    updateDateInView();
                                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), false);
                        timePickerDialog.show();
                    }, year, month, dayOfMonth);
            datePickerDialog.show();
        });


        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CRMReportFragment reportsFragment = new CRMReportFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frame_layout, reportsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        add_Btn.setOnClickListener((v -> {
            sendResReport();
        }));
        setInProgress(false);
        return view;
    }

    void sendResReport() {
        setInProgress(true);

        if (selectedDate == null) {
            AndroidUtil.showToast(getContext(), "Please select a date");
            setInProgress(false);
            return;
        }
        else {

            String transitCheckNameText = transitcheckname.getText().toString();
            String flightNumberText = flightnumber.getText().toString();
            String stationFromInputText = stationFromInput.getText().toString();
            String stationToInputText = stationToInput.getText().toString();
            String departureBBInputText = departureBBInput.getText().toString();
            String departureABInputText = departureABInput.getText().toString();
            String captainAcceptanceInputText = captainAcceptanceInput.getText().toString();
            String arrivalABInputText = arrivalABInput.getText().toString();
            String arrivalBBInputText = arrivalBBInput.getText().toString();
            String actypeText = actype.getText().toString();

            String totalBBInputText = totalBBInput.getText().toString();
            String totalABInputText = totalABInput.getText().toString();
            String readingBeforeRefuelingInputText = readingBeforeRefuelingInput.getText().toString();
            String upliftKgInputText = upliftKgInput.getText().toString();
            String upliftLInputText = upliftLInput.getText().toString();

            String readingAtDepartureInputText = readingAtDepartureInput.getText().toString();
            String readingAtArrivalInputText = readingAtArrivalInput.getText().toString();
            String actualDencityInputText = actualDencityInput.getText().toString();
            String upliftEng1InputText = upliftEng1Input.getText().toString();
            String upliftEng2InputText = upliftEng2Input.getText().toString();

            String readingAtDepartureEng1InputText = readingAtDepartureEng1Input.getText().toString();
            String readingAtDepartureEng2InputText = readingAtDepartureEng2Input.getText().toString();
            String readingAtArrivalEng1InputText = readingAtArrivalEng1Input.getText().toString();
            String readingAtArrivalEng2InputText = readingAtArrivalEng2Input.getText().toString();
            String inspectionCheckTypeInputText = inspectionCheckTypeInput.getText().toString();

            String stampLicenceInputText = stampLicenceInput.getText().toString();
            String stationInputText = stationInput.getText().toString();
            String docRefDocInputText = docRefDocInput.getText().toString();
            String actiontakenText = actiontaken.getText().toString();
            String pirepsmarepsText = pirepsmareps.getText().toString();
            ReportModel reportModel;
//      To get the form data
           reportModel = new ReportModel(actypeText, transitCheckNameText, flightNumberText, stationFromInputText, stationToInputText,
                                departureBBInputText, departureABInputText, captainAcceptanceInputText, arrivalABInputText, arrivalBBInputText,
                                totalBBInputText, totalABInputText, readingBeforeRefuelingInputText, upliftKgInputText, upliftLInputText,
                                readingAtDepartureInputText, readingAtArrivalInputText, actualDencityInputText, upliftEng1InputText, upliftEng2InputText,
                                readingAtDepartureEng1InputText, readingAtDepartureEng2InputText, readingAtArrivalEng1InputText, readingAtArrivalEng2InputText, inspectionCheckTypeInputText,
                                stampLicenceInputText, stationInputText, docRefDocInputText, pirepsmarepsText, actiontakenText, FirebaseUtil.currentUserId(),FirebaseUtil.timestampToString(selectedDate));


//                FirebaseFirestore.getInstance().collection("reports").add(new ReportModel("Airbus A320", "Transit Check", "AC1234", "Station A", "Station B",
//                        "12:00", "13:30", "Captain Acceptance", "14:00", "15:30",
//                        "15000 lbs", "16000 lbs", "5000 lbs", "5500 lbs", "5 L",
//                        "12:30", "15:00", "0.85", "2000 lbs", "2200 lbs",
//                        "10:30", "10:45", "12:15", "12:30", "Visual Inspection",
//                        "Stamped", "Station C", "DocRef123", "No PIREPs or MAREPs", "Actions taken",
//                        FirebaseUtil.currentUserId(),FirebaseUtil.datestampToString(selectedDate)));
                FirebaseFirestore.getInstance().collection("reports").add(reportModel);
                CRMReportFragment reportsFragment = new CRMReportFragment();
                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.main_frame_layout, reportsFragment);
                transaction.addToBackStack(null);
                transaction.commit();
        }
        setInProgress(false);
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

    private void updateDateInView() {
        String selectedDate = String.format("%02d/%02d/%04d %02d:%02d",
                calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH) + 1,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE));
        selectedDateTextView.setText("Selected Date: " + selectedDate);
    }
}













