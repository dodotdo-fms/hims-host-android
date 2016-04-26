package com.dodotdo.himsadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.activity.RoomDetailActivity;
import com.dodotdo.himsadmin.model.Floor;
import com.dodotdo.himsadmin.model.Room;
import com.dodotdo.himsadmin.model.Rooms;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BJ on 2015-03-04.
 */
public class ActionTitleRoomAdapter extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {

    public  Context mContext;
    public static final int VIEWTPYE_ITEM = 1;
    public static final int VIEWTPYE_HEADER = 0;

    ActionTitleRoomAdapter adapter;
    private List<Floor> list;
    OnActionTitleRoomAdapterListener listener;
    public interface OnActionTitleRoomAdapterListener{
        void onItemClick(String floor);
        void onAll(String title);
    }
    public ActionTitleRoomAdapter(Context context) {
        mContext = context;
        adapter = this;
        list = Rooms.getInstance().getFloors();
    }

    public void changeData(){
        this.list = Rooms.getInstance().getFloors();
        notifyDataSetChanged();
    }

    public void setListener(OnActionTitleRoomAdapterListener listener) {
        this.listener = listener;
    }

    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEWTPYE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_action_title_room, parent, false);
                return new ItemView(view);
            case VIEWTPYE_HEADER:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_action_title_room, parent, false);
                return new HeaderView(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemView) {
            ((ItemView) holder).update(list.get(position-1));
        }

        if (holder instanceof HeaderView) {
            ((HeaderView) holder).update();
        }


    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? VIEWTPYE_HEADER : VIEWTPYE_ITEM;
    }



    public  class HeaderView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.floor)
        TextView floor;
        @Bind(R.id.counts)
        TextView counts;
        public HeaderView(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this,view);
            context =view.getContext();
        }

        public void update() {
            this.floor.setText("All Floor");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onAll("All Floor");
                }
            });
        }
    }


    public  class ItemView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.floor)
        TextView floor;
        @Bind(R.id.counts)
        TextView counts;
        public ItemView(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this,view);
            context =view.getContext();
        }

        public void update(final Floor floor) {
            this.floor.setText(floor.getFloor()+"F");
            counts.setText("("+floor.getCount()+")");
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(floor.getFloor());
                }
            });
        }
    }



}