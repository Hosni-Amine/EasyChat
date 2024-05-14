package com.example.easychat.aircraft;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easychat.R;
import com.example.easychat.adapter.GchatRecyclerAdapter;
import com.example.easychat.model.Aircraft;
import com.example.easychat.model.GchatroomModel;
import com.example.easychat.utils.AndroidUtil;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;

public class AirDetailsActivity extends AppCompatActivity {

    private ImageButton colorPickerBtn;

    String gchatroomId;
    Aircraft aircraft;
    EditText messageInput;
    GchatroomModel gchatroomModel;
    GchatRecyclerAdapter adapter;
    ImageButton sendMessageBtn;
    ImageButton backBtn;
    TextView aircname;
    TextView serialNumber;
    TextView manufacture;
    RecyclerView recyclerView;
    ImageView imageView;
    String sendername;
    private int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_airdetails);
        String phoneNumber = getIntent().getExtras().getString("serialNumber");
        aircraft = AndroidUtil.getAirModelFromIntent(getIntent());

        gchatroomId = aircraft.getAircraftId()+"_"+aircraft.getAircraftId();
        messageInput = findViewById(R.id.chat_message_input2);
        sendMessageBtn = findViewById(R.id.message_send_btn2);
        backBtn = findViewById(R.id.back_btn2);
        aircname = findViewById(R.id.air_name);
        serialNumber = findViewById(R.id.serial_number);
        recyclerView = findViewById(R.id.chat_recycler_view2);
        imageView = findViewById(R.id.air_pic_image_view);
        colorPickerBtn = findViewById(R.id.color_picker_btn);

        color = 4;

        aircname.setText("Aircraft name: "+aircraft.getModelName());
        serialNumber.setText("Serial N°: "+phoneNumber);
        colorPickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCustomColorPickerDialog();
            }
        });

        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        sendername = document.getString("username");
                    } else {
                        Log.d("Firebase", "No such document");
                    }
                } else {
                    Log.d("Firebase", "get failed with ", task.getException());
                }
            }
        });

        backBtn.setOnClickListener((v)->{
            onBackPressed();
        });

        sendMessageBtn.setOnClickListener((v -> {
            String message = messageInput.getText().toString().trim();
            if(message.isEmpty())
                return;
            sendMessageToUser(message);
        }));

        getOrCreateChatroomModel();
        setupChatRecyclerView();
    }

    void setupChatRecyclerView(){
        Query query = FirebaseUtil.getGchatroomMessageReference(gchatroomId)
                .orderBy("timestamp", Query.Direction.DESCENDING);

        FirestoreRecyclerOptions<ChatMessageModel> options = new FirestoreRecyclerOptions.Builder<ChatMessageModel>()
                .setQuery(query,ChatMessageModel.class).build();

        adapter = new GchatRecyclerAdapter(options,getApplicationContext());
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setReverseLayout(true);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(adapter);
        adapter.startListening();
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                recyclerView.smoothScrollToPosition(0);
            }
        });
    }
    void sendMessageToUser(String message){

        gchatroomModel.setLastMessageTimestamp(Timestamp.now());
        gchatroomModel.setLastMessageSenderId(FirebaseUtil.currentUserId());
        gchatroomModel.setLastMessage(message);

        FirebaseUtil.getGchatroomReference(gchatroomId).set(gchatroomModel);
        ChatMessageModel chatMessageModel = new ChatMessageModel(message,FirebaseUtil.currentUserId(),Timestamp.now(),sendername,color);
        FirebaseUtil.getGchatroomMessageReference(gchatroomId).add(chatMessageModel)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task) {
                        if(task.isSuccessful()){
                            messageInput.setText("");
                        }
                    }
                });
    }
    void getOrCreateChatroomModel(){
        FirebaseUtil.getGchatroomReference(gchatroomId).get().addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                DocumentSnapshot document = task.getResult();
                if(document != null && document.exists()){
                    gchatroomModel = document.toObject(GchatroomModel.class);
                } else {
                    gchatroomModel = new GchatroomModel(
                            gchatroomId,
                            aircraft.getAircraftId(),
                            Timestamp.now(),
                            ""
                    );
                    FirebaseUtil.getGchatroomReference(gchatroomId).set(gchatroomModel);
                }
            } else {
                // Handle error
                Exception exception = task.getException();
                Log.e("Firebase", "Error getting document", exception);
            }
        }).addOnFailureListener(e -> {
            Log.e("Firebase", "Error getting document", e);
        });
    }


    private void showCustomColorPickerDialog() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_custom_color_picker);
        View colorOption1 = dialog.findViewById(R.id.color_option_1);
        View colorOption2 = dialog.findViewById(R.id.color_option_2);
        View colorOption3 = dialog.findViewById(R.id.color_option_3);
        View colorOption4 = dialog.findViewById(R.id.color_option_4);

        colorOption1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerBtn.setBackgroundColor(ContextCompat.getColor(AirDetailsActivity.this, R.color.color_option_1));
                color = 1;
                dialog.dismiss();
            }
        });

        colorOption2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerBtn.setBackgroundColor(ContextCompat.getColor(AirDetailsActivity.this, R.color.color_option_2));
                color = 3;
                dialog.dismiss();
            }
        });
        colorOption3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerBtn.setBackgroundColor(ContextCompat.getColor(AirDetailsActivity.this, R.color.color_option_3));
                color = 4;
                dialog.dismiss();
            }
        });
        colorOption4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorPickerBtn.setBackgroundColor(ContextCompat.getColor(AirDetailsActivity.this, R.color.color_option_4));
                color = 2;
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}

