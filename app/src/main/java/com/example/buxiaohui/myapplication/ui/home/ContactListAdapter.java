package com.example.buxiaohui.myapplication.ui.home;

import android.database.DataSetObserver;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.buxiaohui.myapplication.R;
import com.example.buxiaohui.myapplication.ui.add.UserDetailActivity;

import org.jivesoftware.smack.roster.RosterEntry;

import java.util.List;

/**
 * Created by bxh on 1/1/16.
 */

public class ContactListAdapter extends BaseExpandableListAdapter {
    List<RosterEntry> rosterEntries;

    public ContactListAdapter() {
        super();
    }

    public void refresh(List<RosterEntry> set) {
        this.rosterEntries = set;
        notifyDataSetChanged();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getGroupCount() {
        return rosterEntries == null ? 0 : 1;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return null;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return rosterEntries != null ? rosterEntries.size() : 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        GroupViewHolder groupViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_contact_group, null);
            groupViewHolder = new GroupViewHolder(convertView);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        //TODO
        //bind data
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildViewHolder childViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user_contact, parent,false);
            childViewHolder = new ChildViewHolder(convertView);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        //TODO
        //bind data
        RosterEntry rosterEntry = rosterEntries.get(childPosition);
        if(rosterEntry!=null){
            childViewHolder.name.setText(""+rosterEntry.getName());
            childViewHolder.desc.setText(""+rosterEntry.getUser());
            childViewHolder.userIcon.setTag(""+rosterEntry.getName());
            childViewHolder.userIcon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String userName = (String)v.getTag();
                    if(!TextUtils.isEmpty(userName)){
                        UserDetailActivity.open(v.getContext(),userName);
                    }
                }
            });
        }
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public long getCombinedGroupId(long groupId) {
        return 0;
    }

    @Override
    public long getCombinedChildId(long groupId, long childId) {
        return 0;
    }

    @Override
    public void onGroupCollapsed(int groupPosition) {

    }

    @Override
    public void onGroupExpanded(int groupPosition) {

    }

    public static class ChildViewHolder {
        View itemView;
        ImageView userIcon;
        TextView name;
        TextView desc;
        ImageView imgAdd;

        public ChildViewHolder(View itemView) {
            this.itemView = itemView;
            userIcon = (ImageView) itemView.findViewById(R.id.img);
            name = (TextView) itemView.findViewById(R.id.name);
            desc = (TextView) itemView.findViewById(R.id.desc);
            imgAdd = (ImageView) itemView.findViewById(R.id.img_add);
        }
    }

    public static class GroupViewHolder {
        TextView name;

        public GroupViewHolder(View itemView) {
            name = (TextView) itemView.findViewById(R.id.name);
        }
    }

}
