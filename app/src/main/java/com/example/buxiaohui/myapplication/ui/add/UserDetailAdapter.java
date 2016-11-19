package com.example.buxiaohui.myapplication.ui.add;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.utils.StringUtils;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bxh on 1/1/16.
 */

public class UserDetailAdapter extends RecyclerView.Adapter<UserDetailAdapter.Holder> {
    public static String TAG = "UserDetailAdapter";
    List<Object> list;

    public UserDetailAdapter() {
        super();
    }
    public void refreshHead(Account entry){
        if(list == null){
            list = new ArrayList<>();
        }
        list.add(0,entry);
        notifyDataSetChanged();
    }
    public List<?> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position) instanceof Account) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        int count = list != null ? list.size() : 0;
        return count;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        holder.bindData(list.get(position), position);
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 0) {
            return new Holder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.view_user_simple_info, parent, false), viewType);
        } else {
            return new Holder(LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_user_detail, parent, false), viewType);
        }
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView head;
        TextView nikeName;
        TextView userName;

        ImageView icon;
        TextView detail0;
        TextView detail1;

        public Holder(View itemView, int viewType) {
            super(itemView);
            if (viewType == 0) {
                head = (ImageView) itemView.findViewById(R.id.id_user_img);
                userName = (TextView) itemView.findViewById(R.id.id_user_name);
                nikeName = (TextView) itemView.findViewById(R.id.id_user_nikename);
            } else {
                icon = (ImageView) itemView.findViewById(R.id.img);
                detail0 = (TextView) itemView.findViewById(R.id.name);
                detail1 = (TextView) itemView.findViewById(R.id.desc);
            }
        }

        public void bindData(Object data, int position) {
            if (data instanceof Account) {
                //TODO
                Account entry = (Account)data;
                nikeName.setText(StringUtils.safe(entry.getNikeName()));
                userName.setText(StringUtils.safe(entry.getUserName()));
            } else {
                //TODO
            }
        }
    }

}
