package com.dodotdo.himsadmin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.adapter.EmployeeListMembersAdapter;
import com.dodotdo.himsadmin.adapter.RoomAssignListAdapter;
import com.dodotdo.himsadmin.adapter.RoomListAdapter;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
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
import butterknife.OnClick;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RoomAssignActivity extends AppCompatActivity {
    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    Toolbar toolbar;
    @Bind(R.id.search)
    EditText search;
    @Bind(R.id.select)
    TextView select;
    @Bind(R.id.btn_assign)
    Button mBtn_assign;
    RoomAssignListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_assign);
        ButterKnife.bind(this);
        toolbarInit();
        recyclerViewInit();
        layoutInit();

    }

    private void layoutInit() {
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

    private void recyclerViewInit() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RoomAssignListAdapter(getApplicationContext(), Rooms.getInstance().getList());
        adapter.setListener(new RoomAssignListAdapter.OnRoomAssignListAdapterListener() {
            @Override
            public void checkChanged() {
                select.setText(adapter.getSelectedText());
                checkEnabledAssign();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void toolbarInit() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        TextView mTv_title = (TextView) findViewById(R.id.tv_title);
        mTv_title.setText("Room assign");
        mBtn_assign.setEnabled(false);
    }

    private void checkEnabledAssign() {
        if(adapter.getCheckRoomNumbers().size()>0){
            mBtn_assign.setEnabled(true);
        }else{
            mBtn_assign.setEnabled(false);
        }
    }

    @OnClick(R.id.btn_assign)
    public void goAssign(){
        setResult(RESULT_OK,getIntent().putStringArrayListExtra("data",adapter.getCheckRoomNumbers()));
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
