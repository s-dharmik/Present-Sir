package com.example.smartsams.Eventdashboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smartsams.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class event_adapter extends RecyclerView.Adapter<event_adapter.event_hold_value>
{

    ArrayList<event_dashboard> event_dash;

    public event_adapter(ArrayList<event_dashboard> event_dash) {
        this.event_dash = event_dash;
    }

    @NonNull
    @Override
    public event_hold_value onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_card_desgine, parent, false);
        event_hold_value event_hold_value = new event_hold_value(view);
        return event_hold_value;
    }

    @Override
    public void onBindViewHolder(@NonNull event_hold_value holder, int position) {

        event_dashboard event_dashboard = event_dash.get(position);

        holder.image.setImageResource(event_dashboard.getImage());
        holder.title.setText(event_dashboard.getTitle());
        holder.desc.setText(event_dashboard.getDescription());
        holder.date.setText(event_dashboard.getDate());

    }

    @Override
    public int getItemCount() {

        return event_dash.size();
    }

    public static class event_hold_value extends RecyclerView.ViewHolder{

        ImageView image;
        TextView title, desc, date;

        public event_hold_value(@NonNull View itemView) {
            super(itemView);

            //Hooks
            image = itemView.findViewById(R.id.event_image_upload);
            title = itemView.findViewById(R.id.event_title);
            desc = itemView.findViewById(R.id.event_description);
            date = itemView.findViewById(R.id.date_event);


        }
    }
}
