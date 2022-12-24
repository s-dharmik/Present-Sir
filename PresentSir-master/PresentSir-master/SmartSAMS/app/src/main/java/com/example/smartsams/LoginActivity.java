package com.example.smartsams;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

import com.example.smartsams.Director.Director_home;
import com.example.smartsams.HOD.HOD_home;
import com.example.smartsams.Student.Student_home;
import com.example.smartsams.Teacher.Teacher_home;

public class LoginActivity extends AppCompatActivity {

    private  ProgressBar loading;
    private  Button btn_login;
    private  EditText email;
    private  EditText password;


    private static String URL_LOGIN = "https://codmans.com/login_user.php";
    SessionManager sessionManager;

    public LoginActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        loading = (ProgressBar) findViewById(R.id.login_loading);
        btn_login = (Button) findViewById(R.id.btn_login);
        email = (EditText) findViewById(R.id.login_email);
        password = (EditText) findViewById(R.id.login_password);
      //  link_reg = (TextView) findViewById(R.id.link_reg);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String mEmail = email.getText().toString().trim();
                String mPassword = password.getText().toString().trim();

                if (!mEmail.isEmpty() || !mPassword.isEmpty()){
                    Login(mEmail, mPassword);
                }
                else{
                    email.setError("Please insert Email");
                    password.setError("Please insert Password");
                }

            }
        });

//        link_reg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
//            }
//        });
    }

    private void MLoging() {
        if (!isConnected(this)) {

            showCustomDialog();
        }

    }





//    Check
//    Internet
//    Connection
    private boolean isConnected(LoginActivity loginActivity) {

    ConnectivityManager connectivityManager = (ConnectivityManager) loginActivity.getSystemService(Context.CONNECTIVITY_SERVICE);

    NetworkInfo wificonn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);
    NetworkInfo mobileconn = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);

    if ((wificonn != null && wificonn.isConnected()) || (mobileconn != null && mobileconn.isConnected()))
    {
        return true;
    }
    else {
        return false;
    }
}
    private void showCustomDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setMessage("Please Connect to the Internet")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                        finish();
                    }
                });
}

    private void Login(String email, String password) {

        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("login");
                            System.out.println("3");

                            if (success.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();
                                    String role = object.getString("role").trim();



                                    sessionManager.createSession(name, email,id, role);


                                    if (role.equals("01"))
                                    {
                                        startActivity(new Intent(getApplicationContext(), Student_home.class));
                                        finish();
                                    }
                                    if(role.equals("02"))
                                    {
                                        startActivity(new Intent(getApplicationContext(), Teacher_home.class));
                                        finish();
                                    }
                                    if(role.equals("03"))
                                    {
                                        startActivity(new Intent(getApplicationContext(), HOD_home.class));
                                        finish();
                                    }
                                    if(role.equals("04"))
                                    {
                                        startActivity(new Intent(getApplicationContext(), Director_home.class));
                                        finish();
                                    }

//                                    Toast.makeText(LoginActivity.this, "Wel Come " +name, Toast.LENGTH_SHORT).show();
//                                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
//                                    intent.putExtra("name", name);
//                                    intent.putExtra("email", email);
//                                    startActivity(intent);
//                                    finish();
//                                    loading.setVisibility(View.GONE);
                                }

                            }
                            else if (success.equals("0")){
                                Toast.makeText(LoginActivity.this, "Check UserID and password", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(LoginActivity.this, e.getMessage() , Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
                Toast.makeText(LoginActivity.this, "Check Internet Connection", Toast.LENGTH_SHORT).show();
                MLoging();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }
}