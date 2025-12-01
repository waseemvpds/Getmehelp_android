package com.example.getmehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText usname, paswrd;
    Button b1, b2;
    SharedPreferences sh;
    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usname = findViewById(R.id.editTextTextPersonName);
        paswrd = findViewById(R.id.editTextTextPersonName2);
        b1 = findViewById(R.id.button);
        b2 = findViewById(R.id.button16);

        // Get the URL from the Intent that started this activity
        url = getIntent().getStringExtra("url");
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent k = new Intent(getApplicationContext(), Register.class);
                // Pass the URL to the Register activity
                k.putExtra("url", url);
                startActivity(k);
            }
        });

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String un = usname.getText().toString();
                String ps = paswrd.getText().toString();

                if (url == null || url.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Backend URL not configured. Please restart the app.", Toast.LENGTH_LONG).show();
                    return;
                }

                String loginUrl = url + "/user_login";

                int flag = 0;
                if (un.isEmpty()) {
                    usname.setError("Please enter a valid email");
                    flag++;
                }
                if (ps.isEmpty()) {
                    paswrd.setError("Please Enter a Password");
                    flag++;
                }

                if (flag == 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, loginUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            String lid = jsonObj.getString("lid");
                                            String type = jsonObj.getString("type");

                                            SharedPreferences.Editor ed = sh.edit();
                                            ed.putString("lid", lid);
                                            ed.putString("type", type);
                                            ed.apply();

                                            Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();

                                            // The original code had a strict check for `type.equalsIgnoreCase(\"user\")`.
                                            // This was a likely source of silent failure. We now navigate to the home
                                            // screen on ANY successful login, which is more robust.
                                            Intent i = new Intent(getApplicationContext(), home2.class);
                                            startActivity(i);
                                            finish(); // Prevent user from going back to login screen

                                        } else {
                                            Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error parsing response: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Login failed: " + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("Name", un);
                            params.put("password", ps);
                            return params;
                        }
                    };

                    postRequest.setRetryPolicy(new DefaultRetryPolicy(
                            100000,
                            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(postRequest);
                }
            }
        });
    }
}