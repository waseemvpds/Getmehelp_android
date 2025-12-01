package com.example.getmehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Register extends AppCompatActivity {
    EditText Name, Email, Phnumber, place, landmark, password, cpassword;
    Button b1;
    RadioGroup gender;
    RadioButton male, female;
    SharedPreferences sh;
    String url;
    String gender1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Name = findViewById(R.id.editTextTextPersonName5);
        Email = findViewById(R.id.editTextTextPersonName6);
        Phnumber = findViewById(R.id.editTextTextPersonName7);
        place = findViewById(R.id.editTextTextPersonName11);
        landmark = findViewById(R.id.editTextTextPersonName12);
        password = findViewById(R.id.editTextTextPersonName13);
        cpassword = findViewById(R.id.editTextTextPersonName14);
        b1 = findViewById(R.id.button4);
        gender = findViewById(R.id.radioGroup);
        male = findViewById(R.id.radioButton);
        female = findViewById(R.id.radioButton2);

        // Get the URL from the Intent that started this activity
        url = getIntent().getStringExtra("url");
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = Name.getText().toString();
                String email = Email.getText().toString();
                String phnumber = Phnumber.getText().toString();
                String Place = place.getText().toString();
                String Landmark = landmark.getText().toString();
                String Password = password.getText().toString();
                String Cpassword = cpassword.getText().toString();

                if (male.isChecked()) {
                    gender1 = "male";
                } else if (female.isChecked()) {
                    gender1 = "female";
                }

                String em_patern = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
                String ps_pattern = "^(?=.*[a-zA-Z])(?=.*\\d).{6,}$";
                String ph_pattern = "^[6-9]\\d{9}$";

                int flag = 0;
                if (name.isEmpty()) {
                    Name.setError("Please Enter the Name.");
                    flag++;
                }
                if (!email.matches(em_patern)) {
                    Email.setError("Please Enter Valid Email");
                    flag++;
                }
                if (!phnumber.matches(ph_pattern)) {
                    Phnumber.setError("Please Enter Valid Phone Number");
                    flag++;
                }
                if (Place.isEmpty()) {
                    place.setError("Please Enter Your Place");
                    flag++;
                }
                if (Landmark.isEmpty()) {
                    landmark.setError("Please Enter Your Landmark");
                    flag++;
                }
                if (!Password.matches(ps_pattern)) {
                    password.setError("Please Enter Valid Password");
                    flag++;
                }
                if (!Cpassword.equals(Password)) {
                    cpassword.setError("Passwords do not match");
                    flag++;
                }

                if (flag == 0) {
                    if (url == null || url.isEmpty()) {
                        Toast.makeText(getApplicationContext(), "Backend URL not configured. Please restart the app.", Toast.LENGTH_LONG).show();
                        return;
                    }

                    String registerUrl = url + "/user_register";

                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest postRequest = new StringRequest(Request.Method.POST, registerUrl,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonObj = new JSONObject(response);
                                        if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                            Toast.makeText(getApplicationContext(), "Registration successful! Please log in.", Toast.LENGTH_LONG).show();
                                            Intent i = new Intent(getApplicationContext(), login.class);
                                            i.putExtra("url", url); // Pass the URL back to the login screen
                                            startActivity(i);
                                            finish(); // Finish the registration activity
                                        } else {
                                            Toast.makeText(getApplicationContext(), "Registration failed: " + jsonObj.getString("message"), Toast.LENGTH_LONG).show();
                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Registration failed: " + error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("name", name);
                            params.put("email", email);
                            params.put("phone", phnumber);
                            params.put("place", Place);
                            params.put("landmark", Landmark);
                            params.put("password", Password);
                            params.put("cpassword", Cpassword);
                            params.put("longitude", sh.getString("longi", ""));
                            params.put("latitude", sh.getString("lati", ""));
                            params.put("gender", gender1);
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