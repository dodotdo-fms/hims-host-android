package com.dodotdo.himsadmin.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.model.Clean;
import com.dodotdo.himsadmin.model.Room;
import com.dodotdo.himsadmin.model.Rooms;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.response.Results;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RoomDetailActivity extends AppCompatActivity {

    @Bind(R.id.number)
    TextView mTv_number;
    @Bind(R.id.status)
    TextView mTv_foStatus;
    @Bind(R.id.housekeeper_name)
    TextView mTv_housekeeperName;
    @Bind(R.id.supervisor_name)
    TextView supervisorName;
    @Bind(R.id.inspection_name)
    TextView mTv_inspectionNAme;
    @Bind(R.id.bar)
    ImageView mIv_bar;
    @Bind(R.id.tv_title)
    TextView title;
    @Bind(R.id.btn_assign)
    Button mBtn_assign;
    @Bind(R.id.btn_unAssign)
    ImageButton mBtn_unAssign;
    Toolbar toolbar;
    Room data;
    DynamicBox box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ButterKnife.bind(this);
        box = new DynamicBox(this,findViewById(R.id.contentView));
        toolbarInit();
        data = Rooms.getInstance().getRoom(getIntent().getStringExtra("roomNumber"));//getData
        initLayout();
    }

    private void toolbarInit() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("");
    }

    private void initLayout() {
        title.setText(data.getRoomNumber());
        mTv_number.setText(data.getRoomNumber());
        String status = data.getRoomStatus().get(0).getFOStatus();
        mTv_foStatus.setText(status);
        if(data.getClean() != null && data.getClean().size() != 0) {
            try {
                mTv_housekeeperName.setText(data.getClean().get(0).getEmployee().getLastName());
            }catch (Exception e){
            }
            mBtn_assign.setVisibility(View.GONE);
            mBtn_unAssign.setVisibility(View.VISIBLE);
        }else{
            mTv_housekeeperName.setText("");
            mBtn_assign.setVisibility(View.VISIBLE);
            mBtn_unAssign.setVisibility(View.GONE);
        }

        if(status.equals("VC") || status.equals("OC")){
            mIv_bar.setImageResource(R.drawable.bar_room_blue);
            mTv_foStatus.setTextColor(getResources().getColor(R.color.bar_blue));
        }else{
            mIv_bar.setImageResource(R.drawable.bar_room_red);
            mTv_foStatus.setTextColor(getResources().getColor(R.color.bar_red));
        }

    }

    @OnClick(R.id.btn_assign)
    public void onAssign(){
        startActivityForResult(new Intent(getApplicationContext(),RoomAssignEmployeeAcitivity.class).setAction("fromRoomDetail"),2);
    }

    @OnClick(R.id.btn_unAssign)
    public void onUnAssign(){
        box.showLoadingLayout();
        ServerQuery.unAssignEmployeeToClean(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()){
                    data.removeClean();
                    initLayout();
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.toString());
                box.hideAll();
            }
        },data.getClean().get(0).getEmployee().getUserid(),data.getRoomNumber());
    }

    @OnClick(R.id.btn_inspection)
    public void onInspection(){

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2){
            if(resultCode == RESULT_OK){
                goAssign(data.getStringExtra("id"));
            }
        }
    }

    private void goAssign(String employeeId) {
        box.showLoadingLayout();
        ArrayList<String > roomNumbers = new ArrayList<>();
        roomNumbers.add(data.getRoomNumber());
        ServerQuery.assignEmployeeToClean(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()){
                    List<Clean> cleans = ((List<Clean>)((Results<List<Clean>>)response.body()).getResult());
                    for(Clean clean : cleans) {
                        Rooms.getInstance().changeCleanInRoom(clean);
                    }
                    initLayout();
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
}
