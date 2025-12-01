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

public class Custom_View_Fuel_booking_status extends BaseAdapter {
    String[] freid,fname,date,status,ftype,amount,fid;
    private Context context;

    public Custom_View_Fuel_booking_status(Context applicationContext, String[] freid, String[] fname, String[] date, String[] status, String[] ftype, String[] amount, String[] fid) {
        this.context=applicationContext;
        this.freid=freid;
        this.fname=fname;
        this.date=date;
        this.status=status;
        this.ftype=ftype;
        this.amount=amount;
        this.fid=fid;

    }


    @Override
    public int getCount() {
        return fname.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_fuel_booking_status,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView17);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView18);
        TextView tv3 =(TextView)gridView.findViewById(R.id.textView19);
        TextView tv4 =(TextView)gridView.findViewById(R.id.textView20);
        Button b1 = (Button)gridView.findViewById(R.id.button9);

        if(status[i].equalsIgnoreCase("approved")){
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
                ed.putString("fid",freid[i]);
                ed.putString("amount",amount[i]);
                ed.commit();
                Intent k=new Intent(context.getApplicationContext(),Payment.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);

            }
        });


        Button b2 = (Button)gridView.findViewById(R.id.button20);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
                SharedPreferences.Editor ed=sh.edit();
                ed.putString("fid",fid[i]);

                ed.commit();
                Intent k=new Intent(context.getApplicationContext(),Chat.class);
                k.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(k);

            }
        });
//        ImageView im=(ImageView) gridView.findViewById(R.id.imageView10);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);



        tv1.setText(fname[i]);
        tv2.setText(date[i]);
        tv3.setText(ftype[i]);
        tv4.setText(status[i]);



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