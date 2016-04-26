package com.dodotdo.himsadmin.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.activity.LoginActivity;
import com.dodotdo.himsadmin.activity.OnActionTitleListItemClickListener;
import com.dodotdo.himsadmin.model.MyAccount;
import com.dodotdo.himsadmin.utill.MySharedfreferenceManager;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingFramgent extends CommonFragment {
    @Bind(R.id.tv_name)
    TextView mTv_name;
    @Bind(R.id.tv_position)
    TextView mTv_position;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting_framgent, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        mTv_name.setText(MyAccount.getInstance().getData().getLastName());
        mTv_position.setText(MyAccount.getInstance().getData().getPosition());
    }

    @OnClick(R.id.btn_logout)
    public void goLogout(){
        MySharedfreferenceManager.getInstance(getContext()).clearAccount();
        startActivity(new Intent(getContext(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP|Intent.FLAG_ACTIVITY_CLEAR_TOP));
        getActivity().finish();
    }

    @Override
    public RecyclerView.Adapter getActionTitleMenuAdapter() {
        return null;
    }

    @Override
    public String getActionTitle() {
        return "User Info";
    }

    @Override
    public void setActionTitleClickListener(OnActionTitleListItemClickListener listener) {
    }
}
