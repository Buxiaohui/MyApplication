package com.example.buxiaohui.myapplication.ui.add;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by bxh on 1/1/16.
 */

public abstract class AddAdapter<T> extends RecyclerView.Adapter<AddBaseHolder> {
    protected List<T> list;

    public AddAdapter() {
        super();
    }

    public AddAdapter(List<T> list) {
        this.list = list;
    }

    public void refresh(List<T> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(AddBaseHolder holder, int position, List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public T getItem(int position) {
        if (list != null && list.size() > position) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(AddBaseHolder holder, int position) {
        holder.bindData(getItem(position), position);
    }

    @Override
    public AddBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
