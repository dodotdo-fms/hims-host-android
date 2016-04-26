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
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.adapter.EmployeeListAdapter;
import com.dodotdo.himsadmin.adapter.EmployeeMemberListAdapter;
import com.dodotdo.himsadmin.adapter.RoomAssignEmployeeListAdapter;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Clean;
import com.dodotdo.himsadmin.model.Employee;
import com.dodotdo.himsadmin.model.Room;
import com.dodotdo.himsadmin.model.Rooms;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.response.Results;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RoomAssignEmployeeAcitivity extends AppCompatActivity {
    Toolbar toolbar;

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    RoomAssignEmployeeListAdapter adapter;
    DynamicBox box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_assign_employee_acitivity);
        ButterKnife.bind(this);
        box = new DynamicBox(this,findViewById(R.id.contentView));
        toolbarInit();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getEmployee();
    }

    private void getEmployee() {
        box.showLoadingLayout();
        ServerQuery.getEmployees(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Employee> list = (List<Employee>) ((Results<List<Employee>>) response.body()).getResult();
                    adapter = new RoomAssignEmployeeListAdapter(getApplicationContext(), list);
                    recyclerView.setAdapter(adapter);
                    adapter.setListener(new RoomAssignEmployeeListAdapter.OnRoomAssignEmployeeListAdpterListener() {
                        @Override
                        public void goAssignRoom(Employee employee) {
                            if(getIntent().getAction() != null && getIntent().getAction().equals("fromRoomDetail")){
                                setResult(RESULT_OK,getIntent().putExtra("id", employee.getUserid()));
                                finish();
                            }else {
                                startActivityForResult(new Intent(getApplicationContext(), RoomAssignActivity.class).putExtra("id", employee.getUserid()), 1);
                            }
                        }
                    });
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                box.hideAll();
            }
        });
    }

    private void toolbarInit() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setText("Room assign");
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                goAssign(data.getStringArrayListExtra("data"),data.getStringExtra("id"));
            }
        }
    }

    private void goAssign( ArrayList<String> roomNumbers,String employeeId) {
        box.showLoadingLayout();
        ServerQuery.assignEmployeeToClean(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()){
                    List<Clean> cleans = ((List<Clean>)((Results<List<Clean>>)response.body()).getResult());
                    for(Clean clean : cleans) {
                        Rooms.getInstance().changeCleanInRoom(clean);
                    }
                    finish();
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.toString());
                box.hideAll();
            }
        },employeeId, roomNumbers);
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
