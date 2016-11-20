package com.example.buxiaohui.myapplication.ui.Chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by bxh on 11/20/16.
 */

public class TalkAdapter extends BaseAdapter {

    Context context = null;
    ArrayList<HashMap<String, Object>> chatList = null;
    int[] layout;
    String[] from;
    int[] to;


    public TalkAdapter(Context context,
                       ArrayList<HashMap<String, Object>> chatList, int[] layout,
                       String[] from, int[] to) {
        super();
        this.context = context;
        this.chatList = chatList;
        this.layout = layout;
        this.from = from;
        this.to = to;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return chatList.size();
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder = null;
        int who = (Integer) chatList.get(position).get("person");

        convertView = LayoutInflater.from(context).inflate(
                layout[who == 1 ? 0 : 1], null);
        holder = new ViewHolder();
        holder.imageView = (ImageView) convertView.findViewById(to[who * 2 + 0]);
        holder.textView = (TextView) convertView.findViewById(to[who * 2 + 1]);


        System.out.println(holder);
        System.out.println("WHYWHYWHYWHYW");
        System.out.println(holder.imageView);
        holder.imageView.setBackgroundResource((Integer) chatList.get(position).get(from[0]));
        holder.textView.setText(chatList.get(position).get(from[1]).toString());
        return convertView;
    }

    class ViewHolder {
        public ImageView imageView = null;
        public TextView textView = null;

    }


}
