package com.example.buxiaohui.myapplication.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.bean.Account;
import com.example.buxiaohui.myapplication.contract.AddUserContract;
import com.example.buxiaohui.myapplication.utils.ImageUtils;
import com.example.buxiaohui.myapplication.utils.StringUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import java.util.List;

/**
 * Created by bxh on 1/1/16.
 */

public class AddUserResultListAdapter extends RecyclerView.Adapter<AddUserResultListAdapter.Holder> {
    AddUserContract.ViewInterface viewInterface;
    private List<Account> list;

    public AddUserResultListAdapter() {
        super();
    }

    public AddUserResultListAdapter(List<Account> list, AddUserContract.ViewInterface v) {
        this.list = list;
        this.viewInterface = v;
    }

    public AddUserResultListAdapter(List<Account> list) {
        this.list = list;
    }

    public void setView(AddUserContract.ViewInterface v) {
        this.viewInterface = v;
    }

    public void refresh(List<Account> list) {
        this.list = list;
        notifyDataSetChanged();
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
        return list == null ? 0 : list.size();
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Account account = list.get(position);
        holder.name.setText(StringUtils.safe(account.getUserName()));
        holder.desc.setText(StringUtils.safe(account.getUserName()));
        ImageUtils.showImage(holder.userIcon.getContext(), holder.userIcon, StringUtils.safe(account.getUserName()));
        holder.imgAdd.setTag(account);
        holder.imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getTag() != null) {
                    Account account1 = (Account) v.getTag();
                    if (account1 != null && viewInterface!=null) {
                        ToastUtils.show("will add" + "--" + account1.getUserName());
                        viewInterface.addUser(account1);
                    }
                }
            }
        });

    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_frend_result, parent, false);
        Holder holder = new Holder(view);
        return holder;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        ImageView userIcon;
        TextView name;
        TextView desc;
        ImageView imgAdd;

        public Holder(View itemView) {
            super(itemView);
            userIcon = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            imgAdd = (ImageView) itemView.findViewById(R.id.img_add);
        }
    }

}
