package com.example.smartsams.Bluetooth;

import androidx.appcompat.app.AppCompatActivity;

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

import java.util.ArrayList;
import java.util.List;

public class Bluetoothscan extends AppCompatActivity {


    public List<String> MACLIST;

    private static final String URL_GETMAC = "https://codmans.com/get_mac.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetoothscan);
        MACLIST = new ArrayList<>();

        getMac();


    }

    public List<String> getmacdata(List<String> getmacaddress) {
        for (int i = 0; i < getmacaddress.size(); i++) {
            System.out.println(getmacaddress.get(i));

        }
        return getmacaddress;
    }




    private void getMac(){
        StringRequest stringRequest=new StringRequest(Request.Method.GET, URL_GETMAC,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray array = new JSONArray(response);
                            System.out.println(array.length());
                            for(int i=0;i<array.length();i++){
                                JSONObject object = array.getJSONObject(i);
                                String MAC=object.getString("mac_address");
                                //System.out.println(MAC);
                                MACLIST.add(MAC);

                            }
                            getmacdata(MACLIST);

                        }
                        catch (Exception e){

                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


}