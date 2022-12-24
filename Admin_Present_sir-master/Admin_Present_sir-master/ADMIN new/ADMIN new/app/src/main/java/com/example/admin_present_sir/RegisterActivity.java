package com.example.admin_present_sir;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
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

public class RegisterActivity extends AppCompatActivity {


    private ProgressBar loading;
    private  Button btn_login;
    private EditText email;
    private  EditText password;

    private static String URL_LOGIN = "https://codmans.com/login_user.php";
    SessionManager sessionManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);

        loading = (ProgressBar) findViewById(R.id.loading_a);
        btn_login = (Button) findViewById(R.id.btn_Login);
        email = (EditText) findViewById(R.id.email_a);
        password = (EditText) findViewById(R.id.password_a);



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

                            if (success.equals("1")){
                                for (int i = 0; i < jsonArray.length(); i++)
                                {
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String name = object.getString("name").trim();
                                    String email = object.getString("email").trim();
                                    String id = object.getString("id").trim();
                                    System.out.println(id);
                                    sessionManager.createSession(name, email,id);

                                    Intent intent = new Intent(RegisterActivity.this, Selection.class);
                                    startActivity(intent);
                                    finish();
                                    loading.setVisibility(View.GONE);
                                    btn_login.setVisibility(View.VISIBLE);


                                }

                            }
                            else if (success.equals("0")){
                                Toast.makeText(RegisterActivity.this, "Check UserID and password", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_login.setVisibility(View.VISIBLE);

                            }
                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                            loading.setVisibility(View.GONE);
                            btn_login.setVisibility(View.VISIBLE);
                            Toast.makeText(RegisterActivity.this, "try after some time " +e.toString(), Toast.LENGTH_SHORT).show();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.setVisibility(View.GONE);
                btn_login.setVisibility(View.VISIBLE);
                Toast.makeText(RegisterActivity.this, "Error "+error.toString(), Toast.LENGTH_SHORT).show();

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



