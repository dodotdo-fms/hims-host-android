package com.dodotdo.himsadmin.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.fragment.CommonFragment;
import com.dodotdo.himsadmin.fragment.RequirementFragment;
import com.dodotdo.himsadmin.fragment.RoomListFragment;
import com.dodotdo.himsadmin.fragment.SettingFramgent;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.dodotdo.mycustomview.view.tablayout.SlidingTabLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements OnActionTitleListItemClickListener{
    @Bind(R.id.viewPager)
    public ViewPager viewPager;
    @Bind(R.id.tabLayout)
    public TabLayout mainTabLayout;
    public MainViewPageAdapter viewPageAdapter;
    @Bind(R.id.btn_title)
    public Button mBtn_title;
    @Bind(R.id.actionTitleRecyclerView)
    MyRecyclerView actionTitleRecyclerView;
    ImageButton fab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbarInit();
        fabInit();
        viewPagerInit();
    }

    private void viewPagerInit() {
        viewPageAdapter = new MainViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(viewPageAdapter);
        mainTabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);
        mainTabLayout.setTabTextColors(getResources().getColorStateList(R.color.selector_tabcolor));
        mainTabLayout.setSelectedTabIndicatorColor(getResources().getColor(R.color.c19a1d1));
        mainTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                viewPager.setCurrentItem(position);
                actionTitleRecyclerView.setVisibility(View.GONE);
                mBtn_title.setText(MainTab.values()[position].fragment.getActionTitle());
                MainTab.values()[position].fragment.setActionTitleClickListener(MainActivity.this);
                try {
                    actionTitleRecyclerView.setAdapter(MainTab.values()[position].fragment.getActionTitleMenuAdapter());
                }catch (Exception e){
                    Log.e("error",e.toString());
                }
                if(position == 0){
                    fab.setImageResource(R.drawable.write_button);
                    fab.setVisibility(View.VISIBLE);
                }
                if(position == 1){
                    fab.setImageResource(R.drawable.add_employee_button);
                    fab.setVisibility(View.VISIBLE);
                }
                if (position == 2){
                    fab.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void fabInit() {
        fab = (ImageButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(viewPager.getCurrentItem() == 0){
                    startActivityForResult(new Intent(getApplicationContext(),RequirementSendActivity.class),1);
                }
                if(viewPager.getCurrentItem() == 1){
                    startActivityForResult(new Intent(getApplicationContext(),RoomAssignEmployeeAcitivity.class),2);
                }
            }
        });
    }

    private void toolbarInit() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        actionTitleRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mBtn_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAndHideActionTitleRecyclerView();
            }
        });
        actionTitleRecyclerView.setVisibility(View.GONE);
    }

    public void setActionTitleAdpater(CommonFragment fragment){
        mBtn_title.setText(fragment.getActionTitle());
        fragment.setActionTitleClickListener(MainActivity.this);
        try {
            actionTitleRecyclerView.setAdapter(fragment.getActionTitleMenuAdapter());
        }catch (Exception e){
            Log.e("error",e.toString());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class MainViewPageAdapter extends FragmentPagerAdapter {
        public MainViewPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position < MainTab.values().length) {
                return MainTab.values()[position].fragment;
            }
            return null;
        }


        @Override
        public String getPageTitle(int position) {
            if (position < MainTab.values().length) {
                return MainTab.values()[position].title;
            }
            return null;
        }

        @Override
        public int getCount() {
            return MainTab.values().length;
        }
    }

    public enum MainTab {
        REQUIREMENT(new RequirementFragment(),"MESSAGE"),
        ROOM(new RoomListFragment(), "ROOM"),
        SETTING(new SettingFramgent(),"SETUP");
        CommonFragment fragment;
        String title;
        MainTab(CommonFragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        MainTab.values()[viewPager.getCurrentItem()].fragment.onActivityResult(requestCode,resultCode,data);
    }

    public void onActionTitleListItemClick(String title) {
        showAndHideActionTitleRecyclerView();
        mBtn_title.setText(title);
    }

    private void showAndHideActionTitleRecyclerView(){
        if(actionTitleRecyclerView.isShown()){
            actionTitleRecyclerView.setVisibility(View.GONE);
        }else{
            actionTitleRecyclerView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        actionTitleRecyclerView.setVisibility(View.GONE);
    }
}
