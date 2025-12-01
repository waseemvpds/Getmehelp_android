package com.example.getmehelp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class Custom_View_Mechanic_Booking_Status extends BaseAdapter {
    String[]mechid,mname,date,status,amount,mid;
    private Context context;
    public Custom_View_Mechanic_Booking_Status(Context applicationContext, String[] mechid, String[] mname, String[] date, String[] status, String[] amount, String[] mid) {
        this.context=applicationContext;
        this.mechid=mechid;
        this.mname=mname;
        this.date=date;
        this.status=status;
        this.amount= amount;
        this.mid= mid;

    }

    @Override
    public int getCount() {
        return status.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_mechanic_booking_status,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView32);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView35);
        TextView tv3 =(TextView)gridView.findViewById(R.id.textView36);
        Button b1 = (Button)gridView.findViewById(R.id.button11);
        if(status[i].equalsIgnoreCase("Approved")){
            b1.setVisibility(View.VISIBLE);
        }
        else{
            b1.setVisibility(View.INVISIBLE);
        }
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("mechid",mechid[i]);
                ed.putString("amount",amount[i]);
                ed.commit();
                Intent k=new Intent(context.getApplicationContext(),Payment_mech.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);

            }
        });

        Button b2= (Button)gridView.findViewById(R.id.button19);

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("mid",mid[i]);

                ed.commit();
                Intent k=new Intent(context.getApplicationContext(),Chat2.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);

            }
        });

//        ImageView im=(ImageView) gridView.findViewById(R.id.imageView10);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);



        tv1.setText(mname[i]);
        tv2.setText(date[i]);
        tv3.setText(status[i]);



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