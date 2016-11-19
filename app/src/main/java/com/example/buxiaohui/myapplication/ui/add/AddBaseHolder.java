package com.example.buxiaohui.myapplication.ui.add;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by bxh on 11/16/16.
 */

public abstract class AddBaseHolder<T> extends RecyclerView.ViewHolder {

    public AddBaseHolder(View itemView) {
        super(itemView);

    }

    public abstract void bindData(T data, int position);

}
