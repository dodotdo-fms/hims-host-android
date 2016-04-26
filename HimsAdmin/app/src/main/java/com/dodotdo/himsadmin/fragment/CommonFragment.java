package com.dodotdo.himsadmin.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;

import com.dodotdo.himsadmin.activity.MainActivity;
import com.dodotdo.himsadmin.activity.OnActionTitleListItemClickListener;

/**
 * Created by Omjoon on 16. 4. 22..
 */
public abstract class CommonFragment extends Fragment {
    abstract public RecyclerView.Adapter getActionTitleMenuAdapter();
    abstract public String getActionTitle();
    abstract public void setActionTitleClickListener(OnActionTitleListItemClickListener listener);
}
