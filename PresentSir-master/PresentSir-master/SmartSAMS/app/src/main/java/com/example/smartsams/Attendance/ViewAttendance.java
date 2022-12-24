package com.example.smartsams.Attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartsams.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Date;
import java.util.Locale;

public class ViewAttendance extends AppCompatActivity {

    String U_enrollment, U_Status, U_Name, U_Date;
    public ArrayList<String> enrollment;
    public ArrayList<String> status;
    public ArrayList<String> name;
    public ArrayList<String> date;
    RecyclerView AttendanceList;
    RecyclerView.Adapter adapter;

    private static final String URL_GETATTENDANCE = "https://codmans.com/get_attendance.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_attendance);

        getAttendance();

        AttendanceList = findViewById(R.id.View_attendance_list);
        enrollment = new ArrayList<>();
        status = new ArrayList<>();
        name = new ArrayList<>();
        date = new ArrayList<>();

    }


    private void getAttendance(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_GETATTENDANCE,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            System.out.println(array.length());
                            for(int i=0;i<array.length();i++)
                            {
                                JSONObject object = array.getJSONObject(i);
                                String Enrollment=object.getString("enrollment");
                                String Status =object.getString("status");
                                String Name =object.getString("name");
                                String Date =object.getString("date");
                                enrollment.add(Enrollment);
                                status.add(Status);
                                name.add(Name);
                                date.add(Date);
                            }
                            getAttendanceList(enrollment, status, name, date);


                        }
                        catch (Exception e){
                            Toast.makeText(ViewAttendance.this, "inner = "+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ViewAttendance.this, "out = "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void getAttendanceList(ArrayList<String> enrollment, ArrayList<String> status , ArrayList<String> name, ArrayList<String> date) {
        ArrayList<AttendanceArrayList> AttendanceLocation = new ArrayList<>();
        int j = 0;
        for (int i = 0; i < enrollment.size(); i++)
        {
            j++;
            System.out.println(enrollment.get(i));
            System.out.println(status.get(i));
            U_enrollment = enrollment.get(i);
            U_Status = status.get(i);
            U_Name = name.get(i);
            U_Date = date.get(i);

            AttendanceList.setHasFixedSize(true);
            AttendanceList.setLayoutManager(new LinearLayoutManager(this));
            AttendanceLocation.add(new AttendanceArrayList(String.valueOf(j),U_Name,U_enrollment,U_Status, U_Date));

            adapter = new AttendaceAdapter(AttendanceLocation);
            AttendanceList.setAdapter(adapter);

        }
    }

//    private void AttendanceList() {
//        AttendanceList.setHasFixedSize(true);
//        AttendanceList.setLayoutManager(new LinearLayoutManager(this));
//
//
//        ArrayList<AttendanceArrayList> AttendanceLocation = new ArrayList<>();
//        AttendanceLocation.add(new AttendanceArrayList(U_enrollment, U_enrollment,U_enrollment,U_Status));
//
//        adapter = new AttendaceAdapter(AttendanceLocation);
//        AttendanceList.setAdapter(adapter);
//
//    }

}