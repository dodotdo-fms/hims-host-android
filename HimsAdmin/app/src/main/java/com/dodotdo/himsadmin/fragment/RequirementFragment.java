package com.dodotdo.himsadmin.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.activity.MainActivity;
import com.dodotdo.himsadmin.activity.OnActionTitleListItemClickListener;
import com.dodotdo.himsadmin.activity.RequirementDetailActivity;
import com.dodotdo.himsadmin.adapter.ActionTitleRequirementAdapter;
import com.dodotdo.himsadmin.adapter.RequirementListAdapter;
import com.dodotdo.himsadmin.model.Requirement;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.response.GetRequirementsResponse;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RequirementFragment extends CommonFragment {
    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    RequirementListAdapter adapter;
    String actionTitle;
    ActionTitleRequirementAdapter actionTitleRequirementAdapter;
    OnActionTitleListItemClickListener listener;
    String status;
    private boolean loadingMoreContent = false;
    LinearLayoutManager layoutManager;
    DynamicBox box;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
        actionTitleViewInit();
    }

    private void actionTitleViewInit() {
        status = null;
        actionTitle = "All";
        actionTitleRequirementAdapter = new ActionTitleRequirementAdapter(getContext());
        actionTitleRequirementAdapter.setListener(new ActionTitleRequirementAdapter.OnActionTitleRoomAdapterListener() {
            @Override
            public void onItemClick(String title) {
                actionTitle = title;
                status = title;
                refresh();
                listener.onActionTitleListItemClick(actionTitle);
            }

            @Override
            public void onAll(String title) {
                actionTitle = "All";
                status = null;
                refresh();
                listener.onActionTitleListItemClick(actionTitle);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_requirement, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        box = new DynamicBox(getContext(),recyclerView);
        recyclerViewInit(view);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
                swipeRefreshLayout.post(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        refresh();
        ((MainActivity) getActivity()).setActionTitleAdpater(this);
    }

    private void recyclerViewInit(View view) {
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setEmptyView(view.findViewById(R.id.empthyView));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int visibleItemCount = layoutManager.getChildCount();
                int totalItemCount = layoutManager.getItemCount();
                int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount) {
                    showMoreContent();
                }
            }
        });
        adapter = new RequirementListAdapter(getContext(), new ArrayList<Requirement>());
        adapter.setListener(new RequirementListAdapter.onItemClickListener() {
            @Override
            public void onClick(String data) {
                startActivityForResult(new Intent(getContext(), RequirementDetailActivity.class).putExtra("data", data), 1);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void refresh() {
        box.showLoadingLayout();
        ServerQuery.getRequirements(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Requirement> list = ((GetRequirementsResponse) response.body()).getLists();
                    adapter.setList(list);
                    adapter.notifyDataSetChanged();
                    Log.e("adapco", adapter.getItemCount() + "");
                    Log.e("add", "assds");
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error", t.toString());
                box.hideAll();
            }
        }, status, null);
    }

    public void showMoreContent() {
        if (loadingMoreContent == true) {
            return;
        }
        loadingMoreContent = true;
        adapter.setFooterVisible(loadingMoreContent);
        ServerQuery.getRequirements(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Requirement> list = ((GetRequirementsResponse) response.body()).getLists();
                    adapter.addItem(list);
                    adapter.notifyItemRangeChanged(adapter.getItemCount(), list.size());
                    loadingMoreContent = false;
                    adapter.setFooterVisible(loadingMoreContent);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error", t.toString());
            }
        }, status, String.valueOf(adapter.getList().size()));

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Requirement item = new Gson().fromJson(data.getStringExtra("data"), Requirement.class);
                adapter.changeData(item);
            }
        }
    }

    @Override
    public RecyclerView.Adapter getActionTitleMenuAdapter() {
        return actionTitleRequirementAdapter;
    }

    @Override
    public String getActionTitle() {
        return actionTitle;
    }

    @Override
    public void setActionTitleClickListener(OnActionTitleListItemClickListener listener) {
        this.listener = listener;
    }

}
