package com.dodotdo.himsadmin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.adapter.EmployeeMemberListAdapter;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Employee;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class EmployeeMemberListActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_member_list);
        ButterKnife.bind(this);
        recyclerView.setEmptyView(findViewById(R.id.empthyView));
        toolbarInit();
        recyclerViewInit();

    }

    private List<Employee> getData() {
        Type listType = new TypeToken<List<Employee>>() {
        }.getType();
        return new Gson().fromJson(getIntent().getStringExtra("data"), listType);
    }

    private void recyclerViewInit() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new EmployeeMemberListAdapter(this, getData()));
    }

    private void toolbarInit() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setText("Assign Employee");
        mTv_title.setGravity(Gravity.LEFT);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            onBackPressed();
        }

        if (id == android.R.id.home) {
            onBackPressed();
        }
        return true;
    }
}
