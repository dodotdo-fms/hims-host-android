package com.dodotdo.himsadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.activity.RequirementDetailActivity;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Requirement;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BJ on 2015-03-04.
 */
public class EmployeeListMembersAdapter extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {

    public  Context mContext;
    public static final int VIEWTPYE_ITEM = 1;
    private static final int TYPE_DEFAULT = 0;
    private static int TYPE_CURRENT = 0;
    private static final int TYPE_SEARCH = 1;

    EmployeeListMembersAdapter adapter;
    private List<CheckEmployeeStatus> list;
    private List<CheckEmployeeStatus> searchList;

    public EmployeeListMembersAdapter(Context context, List<CheckEmployeeStatus> list) {
        mContext = context;
        adapter = this;
        searchList = new ArrayList<>();
        this.list = list;
    }

    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEWTPYE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_employeelist_member_item, parent, false);
                return new ItemView(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemView) {
            ((ItemView) holder).update(TYPE_CURRENT  == TYPE_DEFAULT ? list.get(position) : searchList.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return (TYPE_CURRENT  == TYPE_DEFAULT ? list.size() : searchList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return VIEWTPYE_ITEM;
    }

    public void onSearch(String s) {
        searchList.clear();
        if(s.length() == 0){
            TYPE_CURRENT = TYPE_DEFAULT;
            notifyDataSetChanged();
            return;
        }
        TYPE_CURRENT = TYPE_SEARCH;
        for(CheckEmployeeStatus item : list){
            if(item.getEmployee().getLastName().toLowerCase().startsWith(s)){
                searchList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    public String getSelectedText() {
        int count = 0;
        for(CheckEmployeeStatus data : list){
            if(data.isChecked()){
                count++;
            }
        }

        if(count != 0){
            return count + " Selected";
        }
        return "";
    }


    public  class ItemView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.check)
        CheckBox check;
        @Bind(R.id.name)
        TextView name;
        public ItemView(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this,view);
            context =view.getContext();
        }

        public void update(final CheckEmployeeStatus data) {
            check.setChecked(data.isChecked());
            name.setText(data.getEmployee().getLastName());
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.setChecked(isChecked);
                    notifyItemChanged(0);
                }
            });
        }
    }



}