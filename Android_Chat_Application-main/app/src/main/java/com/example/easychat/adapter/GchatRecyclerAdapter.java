package com.example.easychat.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.easychat.R;
import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.model.GchatroomModel;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import java.util.Date;


public class GchatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, GchatRecyclerAdapter.GchatroomModelViewHolder> {

    Context context;

    public GchatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options, Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull GchatroomModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        Log.i("haushd","asjd");
        if(model.getSenderId().equals(FirebaseUtil.currentUserId())){
            holder.leftChatLayout.setVisibility(View.GONE);
            holder.rightChatLayout.setVisibility(View.VISIBLE);
            holder.rightSenderName.setText(" ("+FirebaseUtil.datestampToString(model.getTimestamp())+")");
            holder.rightChatTextview.setText(model.getMessage());
            if(model.getColor()==1)
            {
                holder.rightColorLayout.setBackgroundResource(R.drawable.danger_warning);
            }
            else if(model.getColor()==2)
            {
                holder.rightColorLayout.setBackgroundResource(R.drawable.warning_warning);
            }
            else if(model.getColor()==3)
            {
                holder.rightColorLayout.setBackgroundResource(R.drawable.standard_warning);
            }
            else{
                holder.rightColorLayout.setBackgroundResource(R.drawable.nomal_warning);
            }
        }else{
            holder.rightChatLayout.setVisibility(View.GONE);
            holder.leftChatLayout.setVisibility(View.VISIBLE);

            holder.leftChatTextview.setText(model.getMessage());
            holder.leftSenderName.setText(model.getSender()+" ("+FirebaseUtil.datestampToString(model.getTimestamp())+")");
            if(model.getColor()==1)
            {
                holder.rightColorLayout.setBackgroundResource(R.drawable.danger_warning);
            }
            else if(model.getColor()==2)
            {
                holder.rightColorLayout.setBackgroundResource(R.drawable.warning_warning);
            }
            else if(model.getColor()==3)
            {
                holder.rightColorLayout.setBackgroundResource(R.drawable.standard_warning);
            }
            else{
                holder.rightColorLayout.setBackgroundResource(R.drawable.nomal_warning);
            }
        }
    }

    @NonNull
    @Override
    public GchatroomModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.grchat_message_recycler_row,parent,false);
        return new GchatroomModelViewHolder(view);
    }
    class GchatroomModelViewHolder extends RecyclerView.ViewHolder{
        View rightColorLayout,leftColorLayout;
        LinearLayout leftChatLayout,rightChatLayout;
        TextView leftChatTextview,rightChatTextview,rightSenderName,leftSenderName;


        public GchatroomModelViewHolder(@NonNull View itemView) {
            super(itemView);
            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview);
            leftSenderName = itemView.findViewById(R.id.left_sender_name);

            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview);
            rightSenderName = itemView.findViewById(R.id.right_sender_name);

            rightColorLayout = itemView.findViewById(R.id.right);
            leftColorLayout = itemView.findViewById(R.id.left);
        }
    }
}
