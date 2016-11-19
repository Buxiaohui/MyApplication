package com.example.buxiaohui.myapplication.ui.add;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.contract.AddGroupContract;
import com.example.buxiaohui.myapplication.utils.ImageUtils;
import com.example.buxiaohui.myapplication.utils.StringUtils;
import com.example.buxiaohui.myapplication.utils.ToastUtils;

import org.jivesoftware.smack.roster.RosterGroup;

import java.util.List;

/**
 * Created by bxh on 1/1/16.
 */

public class AddGroupListAdapter extends AddAdapter<RosterGroup> {
    AddGroupContract.View viewInterface;

    public AddGroupListAdapter() {
        super();
    }

    public AddGroupListAdapter(List<RosterGroup> list, AddGroupContract.View v) {
        this.list = list;
        this.viewInterface = v;
    }

    public AddGroupListAdapter(List<RosterGroup> list) {
        this.list = list;
    }

    public void setView(AddGroupContract.View v) {
        this.viewInterface = v;
    }

    @Override
    public AddBaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new AddGroupHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_frend_result, parent, false));

    }

    public class AddGroupHolder extends AddBaseHolder<RosterGroup> {
        ImageView userIcon;
        TextView name;
        TextView desc;
        ImageView imgAdd;

        public AddGroupHolder(View itemView) {
            super(itemView);
            userIcon = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            imgAdd = (ImageView) itemView.findViewById(R.id.img_add);
        }

        @Override
        public void bindData(RosterGroup data, int position) {
            final RosterGroup group = data;
            name.setText(StringUtils.safe(group.getName()));
            desc.setText(StringUtils.safe(group.getEntryCount() + ""));
            ImageUtils.showImage(userIcon.getContext(), userIcon, "");
            imgAdd.setTag(group);
            imgAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getTag() != null) {
                        RosterGroup account1 = (RosterGroup) v.getTag();
                        if (account1 != null && viewInterface != null) {
                            ToastUtils.show("will add" + "--" + group.getName());
                            viewInterface.addGroup(account1);
                        }
                    }
                }
            });
        }

    }

}
