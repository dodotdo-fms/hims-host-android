package com.dodotdo.himsadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.activity.EmployeeListActivity;
import com.dodotdo.himsadmin.activity.EmployeeListMemeberActivity;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Employee;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BJ on 2015-03-04.
 */
public class EmployeeListAdapter extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {

    public Context mContext;
    public static final int VIEWTPYE_ITEM = 1;
    public static final int VIEWTPYE_HEADER = 2;
    private EmployeeListActivity.OnAdapterListener listener;

    EmployeeListAdapter adapter;
    HashMap<String, List<Employee>> maps;
    List<ItemViewData> data;
    HashMap<String,ItemViewData> dataMaps;
    public EmployeeListAdapter(Context context, HashMap<String, List<Employee>> datas) {
        mContext = context;
        adapter = this;
        this.maps = datas;
        data = new ArrayList<>();
        dataMaps = new HashMap<>();
        setDatas();
    }

    public ArrayList<String > getCheckedIds() {
        ArrayList<String > result = new ArrayList<>();
        for(ItemViewData item : data){
            if(!item.position.equals("All")) {
                if (item.list != null) {
                    for (CheckEmployeeStatus status : item.list) {
                        if (status.isChecked()) {
                            result.add(status.getEmployee().getUserid());
                        }
                    }
                }
            }
        }
        return result;
    }

    public void setDatas(){
        List<CheckEmployeeStatus> allList = new ArrayList<>();
        for(String position : maps.keySet()){
            List<CheckEmployeeStatus> list = new ArrayList<>();
            for(Employee employee : maps.get(position)){
                CheckEmployeeStatus checkEmployeeStatus = new CheckEmployeeStatus(employee,false);
                list.add(checkEmployeeStatus);
                allList.add(checkEmployeeStatus);
            }
            ItemViewData item = new ItemViewData(position,false,list);
            dataMaps.put(item.position,item);
            data.add(item);
        }
        if(data.size() != 0) {
            data.add(0, new ItemViewData("All", false, allList));
        }

    }

    public void changeData(String position,List<CheckEmployeeStatus> list){
        data.get(0).list.removeAll( dataMaps.get(position).list);
        dataMaps.get(position).list = list;
        data.get(0).list.addAll(list);
        notifyDataSetChanged();
    }

    public void setListener(EmployeeListActivity.OnAdapterListener listener){
        this.listener = listener;
    }

    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEWTPYE_HEADER:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_employeelist_header, parent, false);
                return new HeaderView(view);
            case VIEWTPYE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_employeelist, parent, false);
                return new ItemView(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemView) {
            ((ItemView) holder).update(data.get(position));
        }

        if (holder instanceof HeaderView) {
            ((HeaderView) holder).update(data.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEWTPYE_HEADER : VIEWTPYE_ITEM;
    }


    public class ItemView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.check)
        CheckBox check;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.select)
        TextView select;
        @Bind(R.id.left)
        ImageView left;
        public ItemView(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            context = view.getContext();
        }


        public void update(final ItemViewData data) {
            data.check = checkAllSelected(data);
            name.setText(data.position);
            check.setChecked(data.check);
            select.setText(getSelecteText(data,check));
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.check = isChecked;
                    for(CheckEmployeeStatus checkEmployeeStatus : data.list){
                        checkEmployeeStatus.setChecked(isChecked);
                    }
                    select.setText(getSelecteText(data,check));
                    listener.onCheckeCanged();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(data.list != null) listener.onItemClick(data.position,data.list);
                }
            });
        }
    }

    private boolean checkAllSelected(ItemViewData data) {
        int count = 0;
        for(CheckEmployeeStatus item : data.list){
            if(item.isChecked()){
                count++;
            }
        }

        return count == data.list.size() ? true : false ;
    }

    public class HeaderView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.check)
        CheckBox check;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.select)
        TextView select;
        public HeaderView(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            context = view.getContext();
        }


        public void update(final ItemViewData item) {
            item.check = checkAllSelected(item);
            name.setText(item.position);
            check.setChecked(item.check);
            select.setText(getSelecteText(item,check));
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item.check = check.isChecked();
                    for(CheckEmployeeStatus checkEmployeeStatus : item.list){
                        checkEmployeeStatus.setChecked(check.isChecked());
                    }
                    select.setText(getSelecteText(item,check));
                    listener.onCheckeCanged();
                }
            });
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
        }
    }

    private String getSelecteText(ItemViewData data,CheckBox checkBox) {
        String result = "";
        if(data.check){
            result = "All Selected";
            checkBox.setChecked(true);
            return result;
        }
        if(data.position == "All"){
            return "";
        }
        int count = 0;
        for(CheckEmployeeStatus status : data.list){
            if(status.isChecked()){
                count++;
            }
        }
        if(count !=0){
            result = count +" Selected";
        }
        if(count == data.list.size()){
            result = "All Selected";
            checkBox.setChecked(true);
        }
        return result;
    }


    private class ItemViewData{
        public boolean check;
        public List<CheckEmployeeStatus> list;
        public String position;
        public ItemViewData(String position,boolean check, List<CheckEmployeeStatus> list) {
            this.check = check;
            this.list = list;
            this.position = position;
        }
    }


}