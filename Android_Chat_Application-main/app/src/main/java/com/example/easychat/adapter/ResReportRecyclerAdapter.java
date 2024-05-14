package com.example.easychat.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easychat.R;
import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.model.ReportModel;
import com.example.easychat.model.UserModel;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;


public class ResReportRecyclerAdapter extends FirestoreRecyclerAdapter<ReportModel, ResReportRecyclerAdapter.ChatroomModelViewHolder> {

    Context context;

    public ResReportRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ReportModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatroomModelViewHolder holder, int position, @NonNull ReportModel model) {
        FirebaseUtil.currentUserDetails().get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                UserModel currentUserModel = task.getResult().toObject(UserModel.class);
                if (model.getSenderId().equals(currentUserModel.getUserId())) {
                    holder.lastMessageText.setText("Sent by you");

                }
                else{
                    FirebaseUtil.allUserCollectionReference().document(model.getSenderId()).get()
                            .addOnCompleteListener(userTask -> {
                                if (userTask.isSuccessful()) {
                                    UserModel senderUser = userTask.getResult().toObject(UserModel.class);
                                    holder.lastMessageText.setText("Sent by: " + senderUser.getUsername());
                                } else {
                                    holder.lastMessageText.setText("Sent by: Unknown");
                                }
                            });
                }
            }
            holder.lastMessageTime.setText("At: " + FirebaseUtil.datestampToString(model.getTimestamp()));
            holder.usernameText.setText(model.getMessage());
        });
    }

    @NonNull
    @Override
    public ChatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recent_rereport_recycler_row,parent,false);
        return new ChatroomModelViewHolder(view);
    }
    class ChatroomModelViewHolder extends RecyclerView.ViewHolder{
        TextView usernameText;
        TextView lastMessageText;
        TextView lastMessageTime;
        ImageView profilePic;

        public ChatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameText = itemView.findViewById(R.id.user_name_text);
            lastMessageText = itemView.findViewById(R.id.last_message_text);
            lastMessageTime = itemView.findViewById(R.id.last_message_time_text);
            profilePic = itemView.findViewById(R.id.profile_pic_image_view);
        }
    }
}
