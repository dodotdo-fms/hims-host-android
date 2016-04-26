package com.dodotdo.himsadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.model.MyAccount;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.ServiceGenerator;
import com.dodotdo.himsadmin.serverinterface.response.GetLoginResponse;
import com.dodotdo.himsadmin.serverinterface.response.LoginResponse;
import com.dodotdo.himsadmin.utill.MySharedfreferenceManager;
import com.dodotdo.mycustomview.view.CancelAbleEditText;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Omjoon on 16. 3. 24..
 */
public class LoginActivity extends AppCompatActivity {
    @Bind(R.id.tv_email)
    public CancelAbleEditText mEdt_email;
    @Bind(R.id.tv_password)
    public CancelAbleEditText mEdt_password;
    DynamicBox box;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        box = new DynamicBox(this,findViewById(R.id.contentView));
        authCheck();

    }

    private void authCheck() {
        if(MySharedfreferenceManager.getInstance(getApplicationContext()).getUserID()!= null){
            goLogin(MySharedfreferenceManager.getInstance(getApplicationContext()).getUserID(),MySharedfreferenceManager.getInstance(getApplicationContext()).getPasswordID());
        }
    }

    @OnClick(R.id.view_login)
    public void onClickLoginBtn() {
        String id = mEdt_email.getText().trim();
        String password = mEdt_password.getText().trim();
        if(id.length()==0 || password.length()==0){
            Toast.makeText(getApplicationContext(),"please put the text",Toast.LENGTH_SHORT).show();
            return;
        }

        goLogin(id,password);
    }

    private void goLogin(final String id,final String password){
        box.showLoadingLayout();
        ServerQuery.goLogin(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if(response.isSuccess()) {
                    LoginResponse response1 = ((GetLoginResponse) response.body()).getResults();
                    MyAccount.getInstance().setData(response1);
                    MySharedfreferenceManager.getInstance(getApplicationContext()).setUserID(id);
                    MySharedfreferenceManager.getInstance(getApplicationContext()).setPasswordID(password);
                    ServiceGenerator.setToken(response1.getToken());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    finish();
                }else{
                    box.hideAll();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error",t.toString());
                box.hideAll();
            }
        }, id, password);
    }


}
