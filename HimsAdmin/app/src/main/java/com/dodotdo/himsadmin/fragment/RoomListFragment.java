package com.dodotdo.himsadmin.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.dodotdo.himsadmin.adapter.ActionTitleRoomAdapter;
import com.dodotdo.himsadmin.adapter.RoomListAdapter;
import com.dodotdo.himsadmin.model.Requirement;
import com.dodotdo.himsadmin.model.Room;
import com.dodotdo.himsadmin.model.Rooms;
import com.dodotdo.himsadmin.serverinterface.ServerQuery;
import com.dodotdo.himsadmin.serverinterface.response.GetRequirementsResponse;
import com.dodotdo.himsadmin.serverinterface.response.Results;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import mehdi.sakout.dynamicbox.DynamicBox;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RoomListFragment extends CommonFragment  {
    @Bind(R.id.recyclerView)
    MyRecyclerView recyclerView;
    @Bind(R.id.swipeLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    RoomListAdapter adapter;
    ActionTitleRoomAdapter actionTitleRoomAdapter;
    String actionTitle;
    OnActionTitleListItemClickListener listener;
    DynamicBox box;
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
        return inflater.inflate(R.layout.fragment_room_list, container, false);
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        box = new DynamicBox(getContext(),recyclerView);
        recyclerViewInit(view);
        Rooms.getInstance().setListener(new Rooms.OnRoomsChangeListener() {
            @Override
            public void onChange() {
                adapter.notifyDataSetChanged();
                actionTitleRoomAdapter.notifyDataSetChanged();
            }
        });
        actionTitleViewInit();
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
    }

    private void actionTitleViewInit() {
        actionTitleRoomAdapter = new ActionTitleRoomAdapter(getContext());
        actionTitleRoomAdapter.setListener(new ActionTitleRoomAdapter.OnActionTitleRoomAdapterListener() {
            @Override
            public void onItemClick(String floor) {
                adapter.changeData(floor);
                actionTitle = floor+"F";
                listener.onActionTitleListItemClick(actionTitle);
            }

            @Override
            public void onAll(String title) {
                adapter.changeData(null);
                actionTitle = title;
                listener.onActionTitleListItemClick(actionTitle);
            }
        });
        actionTitle = "All Floor";
    }

    private void recyclerViewInit(View view) {
        recyclerView.setEmptyView(view.findViewById(R.id.empthyView));
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    private void refresh() {
        box.showLoadingLayout();
        ServerQuery.getRoom(new Callback() {
            @Override
            public void onResponse(Response response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    List<Room> list = (List<Room>)((Results<List<Room>>) response.body()).getResult();
                    Rooms.getInstance().setData(list);
                    adapter = new RoomListAdapter(getActivity());
                    recyclerView.setAdapter(adapter);
                    actionTitleRoomAdapter.changeData();
                }
                box.hideAll();
            }

            @Override
            public void onFailure(Throwable t) {
                Log.e("error", t.toString());
                box.hideAll();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Rooms.getInstance().setListener(null);
    }

    @Override
    public RecyclerView.Adapter getActionTitleMenuAdapter() {
        return actionTitleRoomAdapter;
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
