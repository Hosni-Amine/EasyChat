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

import com.example.easychat.aircraft.AirDetailsActivity;
import com.example.easychat.R;
import com.example.easychat.model.Aircraft;
import com.example.easychat.utils.AndroidUtil;
import com.example.easychat.utils.FirebaseUtil;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class RecentChatAircraftRecyclerAdapter extends FirestoreRecyclerAdapter<Aircraft, RecentChatAircraftRecyclerAdapter.AircraftViewHolder>  {
    private Context context;

    public RecentChatAircraftRecyclerAdapter(@NonNull FirestoreRecyclerOptions<Aircraft> options, Context context) {
        super(options);
        this.context = context;

    }

    @Override
    protected void onBindViewHolder(@NonNull AircraftViewHolder holder, int position, @NonNull Aircraft model) {
        FirebaseUtil.getOtherAirPicStorageRef(model.getAircraftId()).getDownloadUrl()
                .addOnCompleteListener(t -> {
                    if(t.isSuccessful()){
                        Uri uri  = t.getResult();
                        AndroidUtil.setProfilePic(context,uri,holder.airPic);

                        holder.modelNameText.setText( "Model:"+model.getModelName());
                        holder.manufacturerText.setText("Manufacture:"+model.getManufacturer());
                        holder.serialNumberText.setText("Serial number:"+model.getSerialNumber());

                        // Declare a final reference to the holder
                        final AircraftViewHolder finalHolder = holder;

                        // Set click listener on the itemView
                        finalHolder.itemView.setOnClickListener(v -> {
                            Intent intent = new Intent(context, AirDetailsActivity.class);
                            AndroidUtil.passAirModelAsIntent(intent,model);
                            intent.putExtra("serialNumber",model.getSerialNumber());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(intent);
                        });
                    }
                });
    }

    @NonNull
    @Override
    public AircraftViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_aircraft_recycler_row, parent, false);
        return new AircraftViewHolder(view);
    }

    static class AircraftViewHolder extends RecyclerView.ViewHolder {
        TextView modelNameText;
        TextView manufacturerText;
        TextView serialNumberText;
        ImageView airPic;

        public AircraftViewHolder(@NonNull View itemView) {
            super(itemView);
            modelNameText = itemView.findViewById(R.id.model_name_text);
            manufacturerText = itemView.findViewById(R.id.manufacturer_text);
            serialNumberText = itemView.findViewById(R.id.serial_number_text);
            airPic = itemView.findViewById(R.id.air_pic_image_view);

        }
    }
}
