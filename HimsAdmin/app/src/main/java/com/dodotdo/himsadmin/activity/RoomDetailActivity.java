package com.dodotdo.himsadmin.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.model.Room;
import com.google.gson.Gson;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    Toolbar toolbar;
    Room data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_detail);
        ButterKnife.bind(this);
        toolbarInit();
        data = new Gson().fromJson(getIntent().getStringExtra("data"),Room.class);//getData
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
                mTv_housekeeperName.setText(data.getClean().get(0).getEmployee().getFirstName());
            }catch (Exception e){

            }
        }

        if(status.equals("VC") || status.equals("OC")){
            mIv_bar.setImageResource(R.drawable.bar_room_blue);
            mTv_foStatus.setTextColor(getResources().getColor(R.color.bar_blue));
        }else{
            mIv_bar.setImageResource(R.drawable.bar_room_red);
            mTv_foStatus.setTextColor(getResources().getColor(R.color.bar_red));
        }

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
}
