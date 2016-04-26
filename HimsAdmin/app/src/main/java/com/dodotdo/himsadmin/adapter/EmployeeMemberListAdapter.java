package com.dodotdo.himsadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.activity.EmployeeListActivity;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Employee;
import com.dodotdo.himsadmin.model.ViewModels;
import com.dodotdo.himsadmin.serverinterface.response.Results;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.dodotdo.mycustomview.view.recyclerview.ViewHolderFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BJ on 2015-03-04.
 */
public class EmployeeMemberListAdapter extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {

    public Context mContext;
    public static final int TYPE_TITLE = 1;
    public static final int TYPE_PERSON = 2;

    private EmployeeListActivity.OnAdapterListener listener;
    List<Employee> datas;
    ViewModels data;

    public EmployeeMemberListAdapter(Context context, List<Employee> datas) {
        mContext = context;
        this.datas = datas;
        setDatas();
    }

    public void setDatas(){
        HashMap<String,List<Employee>> employeeMap = new HashMap<>();
        for(Employee employee : datas){
            if(employeeMap.containsKey(employee.getPosition())){
                employeeMap.get(employee.getPosition()).add(employee);
            }else{
                List<Employee> newList = new ArrayList<Employee>();
                newList.add(employee);
                employeeMap.put(employee.getPosition(),newList);
            }
        }
        data = new ViewModels();
        for(String position : employeeMap.keySet()){
            String title = position+" ("+employeeMap.get(position).size()+")";
            data.<String>add(title,TYPE_TITLE);
            for(Employee employee : employeeMap.get(position)){
                data.<Employee>add(employee,TYPE_PERSON);
            }
        }
        notifyDataSetChanged();
    }

    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case TYPE_TITLE:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_member_list_title, parent, false);
                return new TitleViewHolder(view);
            case TYPE_PERSON:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_member_list_item, parent, false);
                return new ItemViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ViewHolderFactory.Updateable) {
            ((ViewHolderFactory.Updateable) holder).update(data.get(position).getData());
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewType();
    }


    public class TitleViewHolder extends RecyclerView.ViewHolder implements ViewHolderFactory.Updateable<String>{
        Context context;
        View view;
        @Bind(R.id.title)
        TextView title;
        public TitleViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            context = view.getContext();
        }

        @Override
        public void update(final String data) {
            title.setText(data);
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder implements ViewHolderFactory.Updateable<Employee>{
        Context context;
        View view;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.position)
        TextView position;
        public ItemViewHolder(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this, view);
            context = view.getContext();
        }

        @Override
        public void update(final Employee data) {
            name.setText(data.getLastName());
            position.setText(data.getPosition());
        }
    }


}