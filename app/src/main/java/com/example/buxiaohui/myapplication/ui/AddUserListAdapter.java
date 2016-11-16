package com.example.buxiaohui.myapplication.ui;

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

public class AddUserListAdapter extends AddAdapter<Account> {
    AddUserContract.ViewInterface viewInterface;
    public AddUserListAdapter() {
        super();
    }

    public AddUserListAdapter(List<Account> list, AddUserContract.ViewInterface v) {
        this.list = list;
        this.viewInterface = v;
    }

    public AddUserListAdapter(List<Account> list) {
        this.list = list;
    }

    public void setView(AddUserContract.ViewInterface v) {
        this.viewInterface = v;
    }

    @Override
    public AddBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddUserHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_frend_result, parent, false));

    }

    public class AddUserHolder extends AddBaseHolder<Account> {
        ImageView userIcon;
        TextView name;
        TextView desc;
        ImageView imgAdd;

        public AddUserHolder(View itemView) {
            super(itemView);
            userIcon = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            imgAdd = (ImageView) itemView.findViewById(R.id.img_add);
        }

        @Override
        public void bindData(Account data, int position) {
            final Account account = data;
            name.setText(StringUtils.safe(account.getUserName()));
            desc.setText(StringUtils.safe(account.getUserName()));
            ImageUtils.showImage(userIcon.getContext(), userIcon, StringUtils.safe(account.getUserName()));
            imgAdd.setTag(account);
            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        Account account1 = (Account) v.getTag();
                        if (account1 != null && viewInterface != null) {
                            ToastUtils.show("will add" + "--" + account1.getUserName());
                            viewInterface.addUser(account1);
                        }
                    }
                }
            });
        }

    }

}
