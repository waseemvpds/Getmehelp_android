package com.example.getmehelp;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Chat2 extends AppCompatActivity {

    MessagesAdapter adapterMessages;
    ListView listMessages;
    Button bt1;
    EditText edtxttosent;
    Handler hnd;
    Runnable ad;
    String url, lid, toid, cname;
    SharedPreferences sh;
    String lastid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        hnd = new Handler();
        listMessages = findViewById(R.id.list_chat);
        bt1 = findViewById(R.id.button_chat_send);
        adapterMessages = new MessagesAdapter(Chat2.this);
        edtxttosent = findViewById(R.id.input_chat_message);
        listMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
        listMessages.setStackFromBottom(true);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        lastid = sh.getString("lastid", "0");
        url = sh.getString("url", "");

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                final String msggg = edtxttosent.getText().toString();
                if (msggg.isEmpty()) {
                    edtxttosent.setError("Enter message");
                    return;
                }

                String chatUrl = url + "/mech_add_chat";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, chatUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("Inserted")) {
                                        edtxttosent.setText("");
                                    } else {
                                        Toast.makeText(getApplicationContext(), "Failed to send message", Toast.LENGTH_LONG).show();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Request failed: " + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("mid", sh.getString("mid", ""));
                        params.put("lid", sh.getString("lid", ""));
                        params.put("message", msggg);
                        return params;
                    }
                };

                postRequest.setRetryPolicy(new DefaultRetryPolicy(100000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);
            }
        });

        ad = new Runnable() {
            @Override
            public void run() {
                String chatUrl = url + "/mech_view_chat";

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, chatUrl,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                        JSONArray jss = jsonObj.getJSONArray("data");
                                        for (int i = 0; i < jss.length(); i++) {
                                            JSONObject js = jss.getJSONObject(i);
                                            ChatMessage message = new ChatMessage();
                                            message.setMessage(js.getString("chat"));
                                            message.setDate(js.getString("date"));

                                            if (js.getString("sid").equalsIgnoreCase(sh.getString("lid", ""))) {
                                                message.setUsername("Me");
                                                message.setIncomingMessage(false);
                                            } else {
                                                message.setUsername("Other"); // Or get the other user's name
                                                message.setIncomingMessage(true);
                                            }
                                            adapterMessages.add(message);
                                            lastid = js.getString("id");
                                        }
                                        listMessages.setAdapter(adapterMessages);
                                        SharedPreferences.Editor edt = sh.edit();
                                        edt.putString("lastid", lastid);
                                        edt.apply();
                                    }
                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                                hnd.postDelayed(ad, 5000);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Request failed: " + error.toString(), Toast.LENGTH_SHORT).show();
                                hnd.postDelayed(ad, 5000); // Retry after 5 seconds on error
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("lastid", lastid);
                        params.put("mid", sh.getString("mid", ""));
                        params.put("lid", sh.getString("lid", ""));
                        return params;
                    }
                };
                requestQueue.add(postRequest);
            }
        };
        hnd.post(ad);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        hnd.removeCallbacks(ad);
    }
}