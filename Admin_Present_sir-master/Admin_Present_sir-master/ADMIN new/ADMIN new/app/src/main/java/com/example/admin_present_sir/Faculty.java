package com.example.admin_present_sir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.basgeekball.awesomevalidation.utility.RegexTemplate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Faculty extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private EditText nameF, emailF, passwordF, confirmpasswordF;
    private Button btn_registF;
    private ProgressBar loadingF;
    private static String URL_REGIST = "https://codmans.com/faculty_register.php";

    public String text;
    public String dep;
    public String role;


    AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faculty);

        loadingF = findViewById(R.id.loadingF);
        nameF = findViewById(R.id.nameF);
        emailF = findViewById(R.id.emailF);
        passwordF = findViewById(R.id.passwordF);
        confirmpasswordF = findViewById(R.id.c_passwordF);
        btn_registF = findViewById(R.id.btn_registF);

        //Initialize Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //name
        awesomeValidation.addValidation(this,R.id.nameF, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
        //email
        awesomeValidation.addValidation(this,R.id.emailF, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        //password
        awesomeValidation.addValidation(this,R.id.passwordF, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",R.string.invalid_password);

        awesomeValidation.addValidation(this,R.id.c_passwordF, R.id.passwordF ,R.string.invalid_confirm_password);

        Spinner dep=findViewById(R.id.departmentF);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.department_list_F , android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dep.setAdapter(adapter);
        dep.setOnItemSelectedListener(this);

        Spinner role=findViewById(R.id.role_F);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.role_list , android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        role.setAdapter(adapter1);
        role.setOnItemSelectedListener(this);





        btn_registF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (awesomeValidation.validate()) {
                    Regist();
                }

                else
                    {
                        Toast.makeText(getApplicationContext(), "Validation Failed", Toast.LENGTH_SHORT).show();

                    }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        text= parent.getItemAtPosition(position).toString();
        if (text.equals("Teacher") || text.equals("HOD") || text.equals("Director"))
        {

            if(text.equals("Teacher") )
            {
                role = "02";
            }
            if(text.equals("HOD") )
            {
                role = "03";
            }
            if(text.equals("Director") )
            {
                role = "04";
            }

        }

        else {
            dep = text;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void Regist() {

            loadingF.setVisibility(View.VISIBLE);
            btn_registF.setVisibility(View.GONE);

            final String name = this.nameF.getText().toString().trim();
            final String email = this.emailF.getText().toString().trim();
            final String password = this.passwordF.getText().toString().trim();

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                String success = jsonObject.getString("success");
                                if (success.equals("1")) {
                                    Toast.makeText(Faculty.this, "Register Success !", Toast.LENGTH_SHORT).show();
                                    loadingF.setVisibility(View.GONE);
                                    btn_registF.setVisibility(View.VISIBLE);
                                    Intent intent= new Intent(Faculty.this,Selection.class);
                                    startActivity(intent);
                                    finish();

                                } else if (success.equals("2")) {

                                    Toast.makeText(Faculty.this, "email already exists!", Toast.LENGTH_SHORT).show();
                                    loadingF.setVisibility(View.GONE);
                                    btn_registF.setVisibility(View.VISIBLE);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                                Toast.makeText(Faculty.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                                loadingF.setVisibility(View.GONE);
                                btn_registF.setVisibility(View.VISIBLE);
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(Faculty.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                            loadingF.setVisibility(View.GONE);
                            btn_registF.setVisibility(View.VISIBLE);

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parms = new HashMap<>();
                    parms.put("name", name);
                    parms.put("email", email);
                    parms.put("password", password);
                    parms.put("role", role);
                    parms.put("department", dep);
                    return parms;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);

        }
    }

