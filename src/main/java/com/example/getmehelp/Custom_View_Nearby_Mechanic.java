package com.example.getmehelp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Custom_View_Nearby_Mechanic extends BaseAdapter {
    String[] mechid,name,email,contact_no,latitude,longitude;
    private Context context;


    public Custom_View_Nearby_Mechanic(Context applicationContext, String[] mechid, String[] name, String[] email, String[] contact_no, String[] latitude, String[] longitude) {

        this.context=applicationContext;
        this.mechid=mechid;
        this.name=name;
        this.email=email;
        this.contact_no=contact_no;
        this.latitude=latitude;
        this.longitude=longitude;
    }


    @Override
    public int getCount() {
        return contact_no.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.activity_custom_view_nearby_mechanic,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView10);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView11);
        TextView tv3 =(TextView)gridView.findViewById(R.id.textView12);
        Button b1 =(Button)gridView.findViewById(R.id.button8);
        ImageView img=(ImageView)gridView.findViewById(R.id.imageView3) ;
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("contact_no",contact_no[i]);
                ed.commit();
                String contact=contact_no[i];
                if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:" + contact_no[i]));
                    callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(callIntent);

                } else {

                    ActivityCompat.requestPermissions((Activity) context, new String[]{Manifest.permission.CALL_PHONE}, 1);
                }

            }
        });
        Button b2 =(Button)gridView.findViewById(R.id.button14);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("mid",mechid[i]);
                ed.commit();
                Intent k=new Intent(context.getApplicationContext(),View_Service_Details.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);
            }
        });
//        b1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//                sh.getString("ip","");
//                String url = sh.getString("url","")+"/book_mechanic";
//
//                RequestQueue requestQueue = Volley.newRequestQueue(context.getApplicationContext());
//                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
//
//                                // response
//                                try {
//                                    JSONObject jsonObj = new JSONObject(response);
//                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                        Toast.makeText(context.getApplicationContext(), "Booking Successfull", Toast.LENGTH_SHORT).show();
//
//
//                                    }
//
//
//                                    // }
//                                    else {
//                                        Toast.makeText(context.getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
//                                    }
//
//                                }    catch (Exception e) {
//                                    Toast.makeText(context.getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // error
//                                Toast.makeText(context.getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//                ) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
//                        Map<String, String> params = new HashMap<String, String>();
//
////                        String id=sh.getString("lid","");
////                        params.put("lid",id);
//////                params.put("mac",maclis);
//                        params.put("mechid",mechid[i]);
//                        params.put("lid",sh.getString("lid",""));
//
//                        return params;
//                    }
//                };
//
//                int MY_SOCKET_TIMEOUT_MS=100000;
//
//                postRequest.setRetryPolicy(new DefaultRetryPolicy(
//                        MY_SOCKET_TIMEOUT_MS,
//                        DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
//                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//                requestQueue.add(postRequest);
//
//            }
//        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("rt_log",mechid[i]);
                ed.commit();
                Intent k=new Intent(context.getApplicationContext(),mechanic__rating.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);
            }
        });
        //        ImageView im=(ImageView) gridView.findViewById(R.id.imageView10);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText(name[i]);
        tv2.setText(email[i]);
        tv3.setText(contact_no[i]);



//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//
//        String url="http://" + ip + ":5000/static/game/"+gamecode[i]+".jpg";
//
//
//        Picasso.with(context).load(url).transform(new CircleTransform()). into(im);

        return gridView;
    }
}