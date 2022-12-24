package com.example.smartsams.Attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartsams.R;
import com.example.smartsams.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Student_view_attendance extends AppCompatActivity {

    private static final String TAG = Student_view_attendance.class.getSimpleName(); // getting the info
    String U_enrollment, U_Status, U_Name, U_Date;
    public ArrayList<String> enrollment;
    public ArrayList<String> status;
    public ArrayList<String> name;
    public ArrayList<String> date;
    RecyclerView AttendanceList;
    RecyclerView.Adapter adapter;
    String getEmail;

    SessionManager sessionManager;
    private static String URL_READ = "https://codmans.com/Student_read_attendance_detail.php";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student_view_attendance);


        AttendanceList = findViewById(R.id.View_attendance_list);
        enrollment = new ArrayList<>();
        status = new ArrayList<>();
        name = new ArrayList<>();
        date = new ArrayList<>();

        // call to session manager class for take value
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getEmail = user.get(sessionManager.EMAIL);
        System.out.println(getEmail);

    }

    //get User Detail
    private void getUserDetail(){

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading.....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){
                                for (int i=0;  i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("name").trim();
                                    String strEnrollment = object.getString("enrollment").trim();
                                    String strStatus = object.getString("status").trim();
                                    String strDate = object.getString("date").trim();

                                    name.add(strName);
                                    enrollment.add(strEnrollment);
                                    status.add(strStatus);
                                    date.add(strDate);
                                }

                                getAttendanceList(enrollment, status, name, date);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(Student_view_attendance.this, "Error Reading Details "+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(Student_view_attendance.this, "Error Reading Details "+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("email", getEmail);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
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
}