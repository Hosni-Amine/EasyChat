package com.example.easychat.adapter;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.easychat.R;
import com.example.easychat.model.ChatMessageModel;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.DocumentSnapshot;

public class ChatRecyclerAdapter extends FirestoreRecyclerAdapter<ChatMessageModel, ChatRecyclerAdapter.ChatModelViewHolder> {

    Context context;

    public ChatRecyclerAdapter(@NonNull FirestoreRecyclerOptions<ChatMessageModel> options,Context context) {
        super(options);
        this.context = context;
    }

    @Override
    protected void onBindViewHolder(@NonNull ChatModelViewHolder holder, int position, @NonNull ChatMessageModel model) {
        DocumentSnapshot documentSnapshot = getSnapshots().getSnapshot(position);
        model.setmessageId(documentSnapshot.getId());

        boolean isCurrentUser = model.getSenderId().equals(FirebaseUtil.currentUserId());
        boolean isImageMessage = model.isImg() == 1;

        if (isCurrentUser) {
            setupRightChat(holder, model, isImageMessage);
        } else {
            setupLeftChat(holder, model, isImageMessage);
        }
    }

    private void setupRightChat(@NonNull ChatModelViewHolder holder, @NonNull ChatMessageModel model, boolean isImageMessage) {
        holder.leftChatLayout.setVisibility(View.GONE);
        holder.rightChatLayout.setVisibility(View.VISIBLE);

        if (isImageMessage) {
            holder.rightImgLayout.setVisibility(View.VISIBLE);
            holder.rightChatTextview.setText("");
            loadImage(holder.rightImgLayout, model.getMessage());
        } else {
            holder.rightChatTextview.setText(model.getMessage());
            holder.rightImgLayout.setVisibility(View.GONE);
        }
        holder.rightTimeview.setText(FirebaseUtil.datestampToString(model.getTimestamp()));
    }

    private void setupLeftChat(@NonNull ChatModelViewHolder holder, @NonNull ChatMessageModel model, boolean isImageMessage) {
        holder.rightChatLayout.setVisibility(View.GONE);
        holder.leftChatLayout.setVisibility(View.VISIBLE);

        if (isImageMessage) {
            holder.leftImgLayout.setVisibility(View.VISIBLE);
            holder.leftChatTextview.setText("");
            loadImage(holder.leftImgLayout, model.getMessage());
        } else {
            holder.leftChatTextview.setText(model.getMessage());
            holder.leftImgLayout.setVisibility(View.GONE);
        }
        holder.leftTimeview.setText(FirebaseUtil.datestampToString(model.getTimestamp()));
    }

    private void loadImage(ImageView imageView, String message) {
        FirebaseUtil.getMessagePicStorageRef(message).getDownloadUrl()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Uri uri = task.getResult();
                        Glide.with(context).clear(imageView);
                        AndroidUtil.setMessagePic(context, uri, imageView);
                    } else {
                        Log.e("ChatRecyclerAdapter", "Image download failed: " + task.getException());
                    }
                });
    }

    @NonNull
    @Override
    public ChatModelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.chat_message_recycler_row,parent,false);
        return new ChatModelViewHolder(view);
    }

    class ChatModelViewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChatLayout,rightChatLayout;
        ImageView leftImgLayout,rightImgLayout;
        TextView leftChatTextview,rightChatTextview,rightTimeview,leftTimeview;

        public ChatModelViewHolder(@NonNull View itemView) {
            super(itemView);

            rightImgLayout = itemView.findViewById(R.id.right_chat_imageview);
            leftImgLayout = itemView.findViewById(R.id.left_chat_imageview);

            leftChatLayout = itemView.findViewById(R.id.left_chat_layout);
            rightTimeview = itemView.findViewById(R.id.right_sender_name);
            leftTimeview = itemView.findViewById(R.id.left_sender_name);
            rightChatLayout = itemView.findViewById(R.id.right_chat_layout);
            leftChatTextview = itemView.findViewById(R.id.left_chat_textview);
            rightChatTextview = itemView.findViewById(R.id.right_chat_textview);
        }
    }
}
