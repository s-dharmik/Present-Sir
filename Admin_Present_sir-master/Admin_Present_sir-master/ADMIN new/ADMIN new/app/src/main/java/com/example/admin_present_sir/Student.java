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

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student<text> extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private EditText name, email, password, Confirmpassword, st_mac_address,joiningyear,enroll;
    private Button btn_regist;
    private ProgressBar loading;
    private static String URL_REGIST = "https://codmans.com/student_register.php";
    private String role;
    public String text;
    public String dep;
    public String sem;

   AwesomeValidation awesomeValidation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        joiningyear = findViewById(R.id.joinyear);
        enroll = findViewById(R.id.enrollment);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        Confirmpassword = findViewById(R.id.Confirmpassword);
        st_mac_address= findViewById(R.id.st_mac_address);
        btn_regist = findViewById(R.id.btn_regist);
        role="01";

        //Initialize Validation
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        //name
        awesomeValidation.addValidation(this,R.id.name, RegexTemplate.NOT_EMPTY,R.string.invalid_name);
      //email
        awesomeValidation.addValidation(this,R.id.email, Patterns.EMAIL_ADDRESS,R.string.invalid_email);
        //enrollment
        awesomeValidation.addValidation(this,R.id.enrollment, "^[0-9]{12}$",R.string.invalid_enroll);
        //join year
        awesomeValidation.addValidation(this,R.id.joinyear, "^[1-2]{1}[0-9]{3}$",R.string.invalid_joinyear);
        //Mac add
        awesomeValidation.addValidation(this,R.id.st_mac_address, "^([0-9A-Fa-f]{2}[:]){5}([0-9A-Fa-f]{2})$",R.string.invalid_macadd);
        //password
        awesomeValidation.addValidation(this,R.id.password, "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$",R.string.invalid_password);

        awesomeValidation.addValidation(this,R.id.Confirmpassword, R.id.password ,R.string.invalid_confirm_password);






        //spinners
        Spinner spinner1= findViewById(R.id.department);
        ArrayAdapter<CharSequence> adapter= ArrayAdapter.createFromResource(this,R.array.department_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter);
        spinner1.setOnItemSelectedListener(this);



        Spinner spinner2= findViewById(R.id.semester);
        ArrayAdapter<CharSequence> adapter1= ArrayAdapter.createFromResource(this,R.array.Semester_list, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter1);
        spinner2.setOnItemSelectedListener(this);

        //spinner end


        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if (awesomeValidation.validate()) {
                    Regist();
                }
                else {
                    Toast.makeText(getApplicationContext(),"Validation Failed",Toast.LENGTH_SHORT).show();

                }
            }

        });
    }



    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
          text= parent.getItemAtPosition(position).toString();
          if (text.equals("1") || text.equals("2") || text.equals("3") || text.equals("4") || text.equals("5") || text.equals("6") || text.equals("7") || text.equals("8")){
              sem = text;
          }
          else {
              dep = text;
          }

    }



    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }



    private void Regist() {
        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);
        //String sp1=spinner1.getSelectedItem().toString();
        //String sp2=spinner2.getSelectedItem().toString();

        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String enrollment = this.enroll.getText().toString().trim();
        final String joinyear = this.joiningyear.getText().toString().trim();
        final String mac = this.st_mac_address.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            if (success.equals("1")) {
                                Toast.makeText(Student.this, "Register Success !", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_regist.setVisibility(View.VISIBLE);
                                Intent intent= new Intent(Student.this,Selection.class);
                                startActivity(intent);
                                finish();
                            }
                            else if (success.equals("2"))
                            {

                                Toast.makeText(Student.this, "email already exists!", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_regist.setVisibility(View.VISIBLE);

                            }
                            else if (success.equals("3"))
                            {

                                Toast.makeText(Student.this, "enrollment already exists!", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_regist.setVisibility(View.VISIBLE);

                            }
                            else if (success.equals("4"))
                            {

                                Toast.makeText(Student.this, "Mac Address already exists!", Toast.LENGTH_SHORT).show();
                                loading.setVisibility(View.GONE);
                                btn_regist.setVisibility(View.VISIBLE);

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(Student.this, "Register Error!" + e.toString(), Toast.LENGTH_SHORT).show();
                            loading.setVisibility(View.GONE);
                            btn_regist.setVisibility(View.VISIBLE);
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Student.this, "Register Error!" + error.toString(), Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parms = new HashMap<>();
                parms.put("name", name);
                parms.put("email", email);
                parms.put("password", password);
                parms.put("enrollment", enrollment);
                parms.put("joinyear", joinyear);
                parms.put("semester",sem);
                parms.put("department",dep);
                parms.put("role", role);
                parms.put("mac_address", mac);
                return parms;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}








