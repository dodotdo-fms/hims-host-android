package com.dodotdo.himsadmin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.adapter.EmployeeListAdapter;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Employee;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.ServiceGenerator;
import com.dodotdo.himsadmin.serverinterface.response.GetLoginResponse;
import com.dodotdo.himsadmin.serverinterface.response.LoginResponse;
import com.dodotdo.himsadmin.serverinterface.response.Results;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;
import com.google.gson.internal.Excluder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;
import retrofit.http.Path;

public class EmployeeListActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    Toolbar toolbar;
    @Bind(R.id.btn_assign)
    Button mBtn_assign;
    EmployeeListAdapter adapter;
    HashMap<String, List<Employee>> employeeMap;
    HashMap<String, Employee> assignEmployeeMap;
    public interface OnAdapterListener {
        void onItemClick(String position, List<CheckEmployeeStatus> data);

        void onCheckeCanged();
    }
    DynamicBox box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list);
        ButterKnife.bind(this);
        box = new DynamicBox(this,findViewById(R.id.contentView));
        employeeMap = new HashMap<>();
        toolbarInit();
        recyclerViewInit();
        setAssignData();
        loadEmployee();

    }

    private void setAssignData() {
        try {
            Type listType = new TypeToken<List<Employee>>() {
            }.getType();
            List<Employee> assignedList = new Gson().fromJson(getIntent().getStringExtra("data"), listType);
            assignEmployeeMap = new HashMap<>();
            for (Employee employee : assignedList) {
                assignEmployeeMap.put(employee.getUserid(), employee);
            }
        } catch (Exception e) {
            //RequirementSendActivity에서 부를경우 assignlist가 없다.
        }
    }

    private void loadEmployee() {
        box.showLoadingLayout();
        ServerQuery.getEmployees(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Employee> list = (List<Employee>) ((Results<List<Employee>>) response.body()).getResult();
                    setDataToAdapter(list);
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                box.hideAll();
            }
        });
    }

    private void setDataToAdapter(List<Employee> list) {
        for (Employee employee : list) {
            if (assignEmployeeMap !=null && !assignEmployeeMap.containsKey(employee.getUserid())) {
                if (employeeMap.containsKey(employee.getPosition())) {
                    employeeMap.get(employee.getPosition()).add(employee);
                } else {
                    List<Employee> newList = new ArrayList<Employee>();
                    newList.add(employee);
                    employeeMap.put(employee.getPosition(), newList);
                }
            }
        }
        adapter = new EmployeeListAdapter(getApplicationContext(), employeeMap);
        recyclerView.setAdapter(adapter);
        adapter.setListener(new OnAdapterListener() {
            @Override
            public void onItemClick(String position, List<CheckEmployeeStatus> data) {
                startActivityForResult(new Intent(getApplicationContext(), EmployeeListMemeberActivity.class).putExtra("position", position).putExtra("data", new Gson().toJson(data)), 1);
            }
            @Override
            public void onCheckeCanged() {
                checkAssignEnabled();
                try{
                    adapter.notifyDataSetChanged();
                }catch (Exception e){}
            }
        });
    }

    private void recyclerViewInit() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setEmptyView(findViewById(R.id.empthyView));
    }

    private void toolbarInit() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setText("Assign Employee");
        mTv_title.setGravity(Gravity.LEFT);
        mBtn_assign.setEnabled(false);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                Type listType = new TypeToken<List<CheckEmployeeStatus>>() {
                }.getType();
                adapter.changeData(data.getStringExtra("position"), (List<CheckEmployeeStatus>) new Gson().fromJson(data.getStringExtra("data"), listType));
                checkAssignEnabled();
            }
        }
    }

    private void checkAssignEnabled() {
        if (adapter.getCheckedIds().size() != 0) {
            mBtn_assign.setEnabled(true);
        } else {
            mBtn_assign.setEnabled(false);
        }
    }

    @OnClick(R.id.btn_assign)
    public void onAssign() {
        setResult(RESULT_OK, getIntent().putStringArrayListExtra("data", adapter.getCheckedIds()));
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.home){
            onBackPressed();
        }

        if(id == android.R.id.home){
            onBackPressed();
        }
        return true;
    }
}
