package com.example.smartsams.Attendance;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smartsams.R;

import java.util.ArrayList;

public class AttendaceAdapter extends RecyclerView.Adapter<AttendaceAdapter.AttendanceListViewHolder> {

    ArrayList<AttendanceArrayList> data;

    public AttendaceAdapter(ArrayList<AttendanceArrayList> data)
    {
        this.data = data;
    }

    @NonNull
    @Override
    public AttendanceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.attendance_list_db,parent,false);
        return new AttendanceListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceListViewHolder holder, int position) {

        AttendanceArrayList title = data.get(position);
        holder.number.setText(title.getNumber());
        holder.name.setText(title.getName());
        holder.enrollment.setText(title.getEnrollment());
        holder.AttendanceStatus.setText(title.getStatus());
        holder.AttendanceDate.setText(title.getDate());

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class AttendanceListViewHolder extends RecyclerView.ViewHolder{
        TextView name,enrollment,number,AttendanceStatus, AttendanceDate;

        public AttendanceListViewHolder(@NonNull View itemView) {
            super(itemView);

            number = itemView.findViewById(R.id.user_number);
            name = itemView.findViewById(R.id.attendance_name);
            enrollment = itemView.findViewById(R.id.attendance_enrollment);
            AttendanceStatus = itemView.findViewById(R.id.attendance_status);
            AttendanceDate = itemView.findViewById(R.id.AT_DATE);
        }
    }
}
