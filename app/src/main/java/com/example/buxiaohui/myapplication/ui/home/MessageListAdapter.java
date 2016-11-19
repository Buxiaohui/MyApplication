package com.example.buxiaohui.myapplication.ui.home;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by bxh on 1/1/16.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.Holder> {
    public MessageListAdapter() {
        super();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

}
