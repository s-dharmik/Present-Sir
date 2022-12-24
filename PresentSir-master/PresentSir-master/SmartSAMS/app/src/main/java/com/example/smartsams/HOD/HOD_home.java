package com.example.smartsams.HOD;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.smartsams.Eventdashboard.event_adapter;
import com.example.smartsams.Eventdashboard.event_dashboard;
import com.example.smartsams.R;
import com.example.smartsams.SessionManager;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.smartsams.Student.Student_home;

public class HOD_home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = HOD_home.class.getSimpleName(); // getting the info
    private TextView name, email;
    SessionManager sessionManager;
    String getId;
    private static String URL_READ = "https://codmans.com/read_detail.php";
//    private static String URL_EDIT = "https://codmans.com/edit_detail.php";
//    private static String URL_UPLOAD = "https://codmans.com/upload.php";

    // variables for student navigation menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar1;

    RecyclerView eventrecyclerView;
    RecyclerView.Adapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_h_o_d_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();


        // define hooks
        drawerLayout = findViewById(R.id.hod_drawer_layout);
        navigationView = findViewById(R.id.hod_nav_view);
        toolbar1 = (Toolbar) findViewById(R.id.hod_toolbar);
        name = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_name);
        email = (TextView) navigationView.getHeaderView(0).findViewById(R.id.user_email);


        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        // set toolbar

        setSupportActionBar(toolbar1);

        // Navigation Drawer Menu
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar1, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        eventrecyclerView = findViewById(R.id.event_recylcerview);
        eventrecyclerView();

    }

    private void eventrecyclerView() {
        eventrecyclerView.setHasFixedSize(true);
        eventrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        ArrayList<event_dashboard> event_location =  new ArrayList<>();

        event_location.add(new event_dashboard(R.drawable.sports, "SPORTS", "Event description is here", "1/01/2021"));
        event_location.add(new event_dashboard(R.drawable.computerscience, "Department Events", "Event description is here","2/2/2021"));
        event_location.add(new event_dashboard(R.drawable.natural, "Natural Events", "Event description is here", "3/2/2021"));

        adapter = new event_adapter(event_location);
        eventrecyclerView.setAdapter(adapter);

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

                                    name.setText(strName);
                                    email.setText(strEmail);
                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            progressDialog.dismiss();
                            Toast.makeText(HOD_home.this, "Error Reading Details "+e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(HOD_home.this, "Error Reading Details "+error.toString(), Toast.LENGTH_SHORT).show();

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

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }
    // when we press back button its closed navigation menu
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }


    // call to navigation button
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.nav_profile:
                Toast.makeText(this, "click on profile", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_about:
                Toast.makeText(this, "click on about", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_share:
                Toast.makeText(this, "click on share", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_rate:
                Toast.makeText(this, "click on rate", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                Toast.makeText(this, "click on logout", Toast.LENGTH_SHORT).show();
                sessionManager.logout();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}