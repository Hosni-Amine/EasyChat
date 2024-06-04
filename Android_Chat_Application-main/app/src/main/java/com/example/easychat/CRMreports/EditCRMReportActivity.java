package com.example.easychat.CRMreports;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.easychat.MainActivity;
import com.example.easychat.R;
import com.example.easychat.model.ReportModel;
import com.example.easychat.user_chat.SearchUserActivity;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;

import org.w3c.dom.Document;

import java.lang.ref.Reference;
import java.util.Date;

public class EditCRMReportActivity extends AppCompatActivity {


    ImageButton backBtn;
    Button editBtn;
    EditText transitcheckname, flightnumber, stationFromInput, stationToInput, departureBBInput, departureABInput,
            captainAcceptanceInput, arrivalABInput, arrivalBBInput, totalBBInput, totalABInput,
            readingBeforeRefuelingInput, upliftKgInput, upliftLInput, readingAtDepartureInput,
            readingAtArrivalInput, actualDencityInput, upliftEng1Input, upliftEng2Input,
            readingAtDepartureEng1Input, readingAtDepartureEng2Input, readingAtArrivalEng1Input,
            readingAtArrivalEng2Input, inspectionCheckTypeInput, stampLicenceInput, stationInput,
            docRefDocInput,pirepsmareps,actiontaken,actype,timefuelcard;

    TextView timespam,updatetimespam;
    ReportModel reportModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_crmreport);
        reportModel = AndroidUtil.getReportModelFromIntent(getIntent());

        backBtn = findViewById(R.id.back_btn);
        editBtn = findViewById(R.id.edit_btn);

        editBtn.setOnClickListener((v)-> {
                    reportModel.setActionTaken(actiontaken.getText().toString());
                    reportModel.setPirepsMareps(pirepsmareps.getText().toString());
                    reportModel.setUpdatetimestamp(FirebaseUtil.datestampToString(Timestamp.now()));

                    FirebaseUtil.crmreportreference(reportModel.getReportId()).set(reportModel)
                            .addOnSuccessListener(aVoid -> {
                                AndroidUtil.showToast(getApplicationContext(),"Report updated successfully");
                                    Intent intent = new Intent(EditCRMReportActivity.this, CRMViewActivity.class);
                                    AndroidUtil.passReportModelAsIntent(intent,reportModel);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    startActivity(intent);
                            })
                            .addOnFailureListener(e -> {
                                AndroidUtil.showToast(getApplicationContext(),"Server error !");
                            });
                    });

        backBtn.setOnClickListener((v)->{
            onBackPressed();
        });
        timefuelcard = findViewById(R.id.timefuelcard);
        timespam = findViewById(R.id.selected_date_text_view);
        updatetimespam = findViewById(R.id.selected_updatedate_text_view);
        actype =findViewById(R.id.actype);
        transitcheckname = findViewById(R.id.transitcheckname);
        flightnumber = findViewById(R.id.flightnumber);
        stationFromInput = findViewById(R.id.stationf);
        stationToInput = findViewById(R.id.stationt);
        departureBBInput = findViewById(R.id.departurebb);
        departureABInput = findViewById(R.id.departureab);
        captainAcceptanceInput = findViewById(R.id.acceptancename);
        arrivalABInput = findViewById(R.id.arrivalAb);
        arrivalBBInput = findViewById(R.id.arrivalBb);
        totalBBInput = findViewById(R.id.totaltimebb);
        totalABInput = findViewById(R.id.totaletimeab);
        readingBeforeRefuelingInput = findViewById(R.id.readingbeforerefuel);
        upliftKgInput = findViewById(R.id.UpliftK);
        upliftLInput = findViewById(R.id.upliftL);
        readingAtDepartureInput = findViewById(R.id.readingatdeparture);
        readingAtArrivalInput = findViewById(R.id.readingafterarrival);
        actualDencityInput = findViewById(R.id.actualdencity);
        upliftEng1Input = findViewById(R.id.engine1uplift);
        upliftEng2Input = findViewById(R.id.engine2uplift);
        readingAtDepartureEng1Input = findViewById(R.id.engine1departure);
        readingAtDepartureEng2Input = findViewById(R.id.engine2departure);
        readingAtArrivalEng1Input = findViewById(R.id.engine1arrival);
        readingAtArrivalEng2Input = findViewById(R.id.engine2arrival);
        inspectionCheckTypeInput = findViewById(R.id.checktype);
        stampLicenceInput = findViewById(R.id.stemplicence);
        stationInput = findViewById(R.id.station);
        docRefDocInput =findViewById(R.id.docref);
        pirepsmareps = findViewById(R.id.pirepsmareps);
        actiontaken = findViewById(R.id.actiontaken);

        timefuelcard.setText(reportModel.getTimefuelcard());
        transitcheckname.setText(reportModel.getPreFlightCheckName());
        flightnumber.setText(reportModel.getFlightNumber());
        stationFromInput.setText(reportModel.getStationFrom());
        stationToInput.setText(reportModel.getStationTo());
        departureBBInput.setText(reportModel.getDepartureBB());
        departureABInput.setText(reportModel.getDepartureAB());
        captainAcceptanceInput.setText(reportModel.getCaptainAcceptance());
        arrivalABInput.setText(reportModel.getArrivalAB());
        actype.setText(reportModel.getActypeText());

        arrivalBBInput.setText(reportModel.getArrivalBB());
        totalBBInput.setText(reportModel.getTotalBB());
        totalABInput.setText(reportModel.getTotalAB());
        readingBeforeRefuelingInput.setText(reportModel.getReadingBeforeRefueling());
        upliftKgInput.setText(reportModel.getUpliftKg());
        upliftLInput.setText(reportModel.getUpliftL());

        readingAtDepartureInput.setText(reportModel.getReadingAtDeparture());
        readingAtArrivalInput.setText(reportModel.getReadingAtArrival());
        actualDencityInput.setText(reportModel.getActualDencity());
        upliftEng1Input.setText(reportModel.getUpliftEng1());
        upliftEng2Input.setText(reportModel.getUpliftEng2());

        readingAtDepartureEng1Input.setText(reportModel.getReadingAtDepartureEng1());
        readingAtDepartureEng2Input.setText(reportModel.getReadingAtDepartureEng2());
        readingAtArrivalEng1Input.setText(reportModel.getReadingAtArrivalEng1());
        readingAtArrivalEng2Input.setText(reportModel.getReadingAtArrivalEng2());
        inspectionCheckTypeInput.setText(reportModel.getInspectionCheckType());

        stampLicenceInput.setText(reportModel.getStampLicence());
        stationInput.setText(reportModel.getStation());
        docRefDocInput.setText(reportModel.getDocRefDoc());
        actiontaken.setText(reportModel.getActionTaken());
        pirepsmareps.setText(reportModel.getPirepsMareps());
        timespam.setText(reportModel.getTimestamp());
        transitcheckname.setEnabled(false);
        timefuelcard.setEnabled(false);
        flightnumber.setEnabled(false);
        stationFromInput.setEnabled(false);
        stationToInput.setEnabled(false);
        departureBBInput.setEnabled(false);
        departureABInput.setEnabled(false);
        captainAcceptanceInput.setEnabled(false);
        arrivalABInput.setEnabled(false);
        arrivalBBInput.setEnabled(false);
        actype.setEnabled(false);
        totalBBInput.setEnabled(false);
        totalABInput.setEnabled(false);
        readingBeforeRefuelingInput.setEnabled(false);
        upliftKgInput.setEnabled(false);
        upliftLInput.setEnabled(false);
        readingAtDepartureInput.setEnabled(false);
        readingAtArrivalInput.setEnabled(false);
        actualDencityInput.setEnabled(false);
        upliftEng1Input.setEnabled(false);
        upliftEng2Input.setEnabled(false);
        readingAtDepartureEng1Input.setEnabled(false);
        readingAtDepartureEng2Input.setEnabled(false);
        readingAtArrivalEng1Input.setEnabled(false);
        readingAtArrivalEng2Input.setEnabled(false);
        inspectionCheckTypeInput.setEnabled(false);
        stampLicenceInput.setEnabled(false);
        stationInput.setEnabled(false);
        docRefDocInput.setEnabled(false);

        if(reportModel.getUpdatetimestamp()!=null){
            updatetimespam.setVisibility(View.VISIBLE);
            updatetimespam.setText("Last update:"+reportModel.getUpdatetimestamp());
        }else{
            updatetimespam.setVisibility(View.INVISIBLE);
        }
    }
}