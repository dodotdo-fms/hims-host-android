package com.dodotdo.himsadmin.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.model.Requirement;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.response.Results;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RequirementSendActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Bind(R.id.tv_selectedPerson)
    TextView mTv_selectedPerson;
    @Bind(R.id.tv_title)
    TextView mTv_title;
    @Bind(R.id.edt_message)
    EditText mEdt_message;
    ArrayList<String > senderIds;
    MenuItem send;
    DynamicBox box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requirement_send);
        ButterKnife.bind(this);
        box = new DynamicBox(this,findViewById(R.id.contentView));
        toolbarInit();
        senderIds = new ArrayList<>();
        layoutInit();

    }

    private void layoutInit() {
        mTv_selectedPerson.setText("To :");
        mEdt_message.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                checkSendEnabled();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void toolbarInit() {
        toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        setTitle("");
        mTv_title.setText("New Message");
        mTv_title.setGravity(Gravity.LEFT);
    }

    private void checkSendEnabled() {
        if(mEdt_message.getText().toString().length()>0 && senderIds.size() > 0){
            send.setEnabled(true);
        }else{
            send.setEnabled(false);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_requirement_send,menu);
        send = menu.findItem(R.id.action_send);
        checkSendEnabled();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_send){
            onSend();
        }
        if(id == R.id.home){
            onBackPressed();
        }
        if(id == android.R.id.home){
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.iv_add)
    public void goAdd(){
        startActivityForResult(new Intent(this,EmployeeListActivity.class),1);
    }

    private void onSend() {
        box.showLoadingLayout();
        ServerQuery.sendRequirement(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()){
                    Requirement data = (Requirement)((Results<Requirement>) response.body()).getResult();
                    setResult(RESULT_OK,getIntent().putExtra("data",new Gson().toJson(data)));
                    finish();
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.toString());
                box.hideAll();
            }
        },mEdt_message.getText().toString(),senderIds);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){
                senderIds = data.getStringArrayListExtra("data");
                changeSelectedText();
                checkSendEnabled();
            }
        }
    }

    private void changeSelectedText() {
        mTv_selectedPerson.setText(senderIds.size()+" Selected");
    }

    @Override
    protected void onResume() {
        super.onResume();
        showKeyboard();
    }

    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEdt_message, InputMethodManager.SHOW_IMPLICIT);
    }
}
