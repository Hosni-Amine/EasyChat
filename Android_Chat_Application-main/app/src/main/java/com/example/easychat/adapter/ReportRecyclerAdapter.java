package com.example.easychat.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easychat.MainActivity;
import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.model.ReportModel;
import com.example.easychat.user_chat.ChatActivity;
import com.example.easychat.R;
import com.example.easychat.model.ChatroomModel;
import com.example.easychat.model.UserModel;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class ReportRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ReportRecyclerAdapter.ChatroomModelViewHolder> {

    Context context;

    public ReportRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatroomModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel currentUserModel = task.getResult().toObject(UserModel.class);
                FirebaseUtil.allUserCollectionReference().document(model.getSenderId()).get()
                        .addOnCompleteListener(userTask -> {
                            if (userTask.isSuccessful()) {
                                UserModel senderUser = userTask.getResult().toObject(UserModel.class);
                                String senderName = model.getSenderId().equals(currentUserModel.getUserId()) ? "you" : senderUser.getUsername();

                                FirebaseUtil.allUserCollectionReference().document(model.getSender()).get()
                                        .addOnCompleteListener(receiverTask -> {
                                            if (receiverTask.isSuccessful()) {
                                                UserModel receiverUser = receiverTask.getResult().toObject(UserModel.class);
                                                String receiverName = receiverUser.getUsername();
                                                holder.reciever_name.setText("Sender : " + senderName );
                                                holder.sender_name.setText("Reciever : " + receiverName );
                                            } else {
                                                holder.reciever_name.setText("Sender : " + senderName );
                                                holder.sender_name.setText("Reciever : Unknown" );
                                            }
                                        });
                            } else {
                                holder.reciever_name.setText("Sender : Unknown");
                                holder.sender_name.setText("Reciever : Unknown");
                            }
                        });
            } else {
                holder.reciever_name.setText("Sender : Unknown");
                holder.sender_name.setText("Reciever : Unknown");
            }
            holder.lastMessageTime.setText("At: " + FirebaseUtil.datestampToString(model.getTimestamp()));
            holder.usernameText.setText(model.getMessage());
        });
    }


    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_report_recycler_row,parent,false);
        return new ChatroomModelViewHolder(view);
    }
    class ChatroomModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView reciever_name;
        TextView lastMessageTime;
        TextView sender_name;
        ImageView profilePic;

        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            reciever_name = itemView.findViewById(R.id.reciever_name);
            sender_name = itemView.findViewById(R.id.sender_name);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}
