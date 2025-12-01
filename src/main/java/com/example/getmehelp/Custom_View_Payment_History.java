package com.example.getmehelp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Custom_View_Payment_History extends BaseAdapter {
    String[] pdate,type,details,pamount,pstatus;
    private Context context;

    public Custom_View_Payment_History(Context applicationContext, String[] pdate, String[] type, String[] details, String[] pamount, String[] pstatus) {
        this.context=applicationContext;
        this.pdate=pdate;
        this.type=type;
        this.details=details;
        this.pamount=pamount;
        this.pstatus=pstatus;
    }


    @Override
    public int getCount() {
        return pamount.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_payment_history,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView34);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView38);
        TextView tv3 =(TextView)gridView.findViewById(R.id.textView46);
        TextView tv4 =(TextView)gridView.findViewById(R.id.textView40);
        TextView tv5 =(TextView)gridView.findViewById(R.id.textView44);
//        ImageView im=(ImageView) gridView.findViewById(R.id.imageView10);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);
        tv5.setTextColor(Color.BLACK);



        tv1.setText(pdate[i]);
        tv2.setText(type[i]);
        tv3.setText(details[i]);
        tv4.setText(pamount[i]);
        tv5.setText(pstatus[i]);



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