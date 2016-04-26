package com.dodotdo.himsadmin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.adapter.EmployeeListMembersAdapter;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Employee;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EmployeeListMemeberActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    List<CheckEmployeeStatus> list;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.select)
    TextView select;
    Toolbar toolbar;
    EmployeeListMembersAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_list_memeber);
        toolbarInit();
        ButterKnife.bind(this);
        getData();
        recyclerViewInit();

    }

    private void getData() {
        Type listType = new TypeToken<List<CheckEmployeeStatus>>() {}.getType();
        list = new Gson().fromJson(getIntent().getStringExtra("data"), listType);
    }

    private void recyclerViewInit() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new EmployeeListMembersAdapter(this,list);
        recyclerView.setAdapter(adapter);
        select.setText(adapter.getSelectedText());
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                adapter.onSearch(s.toString());
            }
        });
    }

    private void toolbarInit() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setText("Assign Employee");
    }

    @OnClick(R.id.btn_done)
    public void onDone(){
        setResult(RESULT_OK,getIntent().putExtra("data",new Gson().toJson(list)));
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
