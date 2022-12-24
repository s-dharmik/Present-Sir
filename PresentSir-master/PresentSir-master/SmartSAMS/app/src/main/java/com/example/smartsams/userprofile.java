package com.example.smartsams;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class userprofile extends AppCompatActivity {

    private static final String TAG = userprofile.class.getSimpleName(); // getting the info

    TextView name, enrollment, department, semester, year;
    TextView Uname, Uemail;
    ImageView imageView;
    String getId, getEmail;

    SessionManager sessionManager;

    private static String URL_READ = "https://codmans.com/read_detail.php";
    private static String URL_READ1 = "https://codmans.com/read_detail_student.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userprofile);

        //Hooks
        Uname = findViewById(R.id.UserName);
        Uemail = findViewById(R.id.UserEmail);
        name = findViewById(R.id.UserName_1);
        enrollment = findViewById(R.id.UserEnrollment);
        department = findViewById(R.id.UserDepartment);
        semester = findViewById(R.id.UserSemester);
        year = findViewById(R.id.UserYear);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);


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
                                    String strEmail = object.getString("email").trim();
                                    String strDepartment = object.getString("department").trim();

                                    Uname.setText(strName);
                                    name.setText(strName);
                                    Uemail.setText(strEmail);
                                    department.setText(strDepartment);
                                    getEmail = strEmail;
                                    getUserDetail1();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(userprofile.this, "Error Reading Details "+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(userprofile.this, "Error Reading Details "+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", getId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }



    //get User Detail
    private void getUserDetail1(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i(TAG, response.toString());

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");

                            if (success.equals("1")){
                                for (int i=0;  i < jsonArray.length(); i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strEnrollment = object.getString("enrollment").trim();
                                    String strSemester = object.getString("semester").trim();
                                    String strJoinYear = object.getString("joinyear").trim();

                                    enrollment.setText(strEnrollment);
                                    semester.setText(strSemester);
                                    year.setText(strJoinYear);

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();

                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

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
        getUserDetail1();
    }

}