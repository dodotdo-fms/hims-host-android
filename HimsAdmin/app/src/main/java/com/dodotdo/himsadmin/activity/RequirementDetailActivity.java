package com.dodotdo.himsadmin.activity;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.load.engine.Resource;
import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.model.Requirement;
import com.dodotdo.himsadmin.model.Room;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.response.Results;
import com.dodotdo.mycustomview.view.ScrollPreventViewPager;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RequirementDetailActivity extends AppCompatActivity {
    Toolbar toolbar;

    @Bind(R.id.tv_title)
    TextView title;

    @Bind(R.id.tv_state)
    TextView mTv_state;

    @Bind(R.id.tv_content)
    TextView mTv_content;

    @Bind(R.id.tv_name)
    TextView mTv_name;

    @Bind(R.id.tv_date)
    TextView mTv_date;

    @Bind(R.id.btn_onit)
    Button mBtn_onit;

    @Bind(R.id.btn_assignList)
    Button mBtn_assignList;

    @Bind(R.id.btn_assign)
    Button mBtn_assign;

    Requirement data;
    int currentPosition;
    DynamicBox box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_detail);
        ButterKnife.bind(this);
        toolbarInit();
        box = new DynamicBox(this,findViewById(R.id.contentView));
        getData();
        initLayout();
    }

    private void getData() {
        data = new Gson().fromJson(getIntent().getStringExtra("data"), Requirement.class);
        currentPosition = getIntent().getIntExtra("position",0);
    }

    private void toolbarInit() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
    }

    private void initLayout() {
        title.setText("Requirement");
        ((TextView)findViewById(R.id.tv_title)).setText("Requirement");
        mTv_state.setText(data.getStatus());
        mTv_content.setText(data.getContentEnglish());
        if(data.getStatus().equals("DONE")){
            mTv_name.setText(data.getDoneEmployee().getLastName());
            mTv_state.setBackgroundColor(getResources().getColor(R.color.bar_green));
            mTv_date.setText(data.getDoneTimestamp());
            mBtn_onit.setVisibility(View.INVISIBLE);
            mBtn_assignList.setVisibility(View.GONE);
            mBtn_assign.setEnabled(false);
        }else if(data.getStatus().equals("WIP")){
            mTv_name.setText(data.getWipEmployee().getLastName());
            mTv_state.setBackgroundColor(getResources().getColor(R.color.bar_red));
            mTv_date.setText(data.getWipTimestamp());
            mBtn_onit.setText("DONE");
            mBtn_onit.setVisibility(View.VISIBLE);
            mBtn_assignList.setVisibility(View.GONE);
            mBtn_assign.setEnabled(false);
        }else{
            mTv_name.setText(data.getAssignedEmployeeList().size() + "Employee");
            mTv_state.setBackgroundColor(getResources().getColor(R.color.bar_gray));
            mTv_date.setText(data.getRegisterTimestamp());
            mBtn_onit.setVisibility(View.VISIBLE);
            if(data.getAssignedEmployeeList().size() != 0){
                mBtn_assignList.setVisibility(View.VISIBLE);
            }else{
                mBtn_assignList.setVisibility(View.GONE);
            }
            mBtn_assign.setEnabled(true);
        }
    }

    @OnClick(R.id.btn_assign)
    public void goAssgin(){
        startActivityForResult(new Intent(getApplicationContext(),EmployeeListActivity.class).putExtra("data",new Gson().toJson(data.getAssignedEmployeeList())),1);
    }

    @OnClick(R.id.btn_assignList)
    public void goAssginList(){
        Log.e("sss","Ass");
        startActivity(new Intent(getApplicationContext(),EmployeeMemberListActivity.class).putExtra("data",new Gson().toJson(data.getAssignedEmployeeList())));
    }

    @OnClick(R.id.btn_onit)
    public void goOnIt(){
        if(data.getStatus().equals("WIP")){
            goStatusChange("DONE");
        }else{//NONE
            goStatusChange("WIP");
        }
    }

    private void goStatusChange(String state) {
        box.showLoadingLayout();
        ServerQuery.putRequirementState(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()){
                    data = (Requirement)((Results<Requirement>) response.body()).getResult();
                    setResult(RESULT_OK,getIntent().putExtra("data",new Gson().toJson(data)));
                    initLayout();
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.toString());
                box.hideAll();
            }
        },String.valueOf(data.getId()),state);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                postAssign(data.getStringArrayListExtra("data"));
            }
        }
    }

    private void postAssign(List<String> ids) {
        box.showLoadingLayout();
        ServerQuery.postRequirementAssign(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()){
                    data = (Requirement)((Results<Requirement>) response.body()).getResult();
                    setResult(RESULT_OK,getIntent().putExtra("data",new Gson().toJson(data)));
                    initLayout();
                }
                box.hideAll();
            }
            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.toString());
                box.hideAll();
            }
        },String.valueOf(data.getId()),ids);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
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
        return false;
    }

}
