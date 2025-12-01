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

public class Custom_View_Reply extends BaseAdapter {
    String[] cid,complaint,cdate,reply,rdate;
    private Context context;
    public Custom_View_Reply(Context applicationContext, String[] cid, String[] complaint, String[] cdate, String[] reply, String[] rdate) {
        this.context=applicationContext;
        this.cid=cid;
        this.complaint=complaint;
        this.cdate=cdate;
        this.reply=reply;
        this.rdate=rdate;
    }


    @Override
    public int getCount() {
        return complaint.length;
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
            gridView=inflator.inflate(R.layout.activity_custom_view_reply,null);

        }
        else
        {
            gridView=(View)view;

        }
        TextView tv1=(TextView)gridView.findViewById(R.id.textView25);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView26);
        TextView tv3 =(TextView)gridView.findViewById(R.id.textView27);
        TextView tv4 =(TextView)gridView.findViewById(R.id.textView28);
//        ImageView im=(ImageView) gridView.findViewById(R.id.imageView10);

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);
        tv4.setTextColor(Color.BLACK);



        tv1.setText(complaint[i]);
        tv2.setText(cdate[i]);
        tv3.setText(reply[i]);
        tv4.setText(rdate[i]);



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