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
import com.dodotdo.himsadmin.activity.RequirementDetailActivity;
import com.dodotdo.himsadmin.activity.RoomDetailActivity;
import com.dodotdo.himsadmin.model.Requirement;
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
public class RoomListAdapter extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {

    public  Context mContext;
    public static final int VIEWTPYE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private boolean footerVisible;

    RoomListAdapter adapter;
    private List<Room> list;
    public RoomListAdapter(Context context) {
        mContext = context;
        adapter = this;
        list = Rooms.getInstance().getList();
    }


    private boolean useFooter() {
        return footerVisible;
    }

    public void setFooterVisible(boolean b) {
        if (footerVisible == b) {
            //no change state
            return;
        }
        footerVisible = b;
        if (footerVisible) {
            //invisible -> visible
            notifyItemInserted(getItemCount() + 1);
        } else {
            //visible -> invisible
            notifyItemRemoved(getItemCount());
        }
    }

    public void changeData(String floor){
        list = Rooms.getInstance().getList(floor);
        notifyDataSetChanged();
    }


    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEWTPYE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_room, parent, false);
                return new ItemView(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemView) {
            ((ItemView) holder).update(list.get(position));
        }


    }

    @Override
    public int getItemCount() {
        return list.size() + (useFooter() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return (position >= list.size() ? TYPE_FOOTER : VIEWTPYE_ITEM);
    }




    public  class ItemView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.leftBar)
        ImageView bar;
        @Bind(R.id.number)
        TextView number;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.timeText)
        TextView tiemText;
        @Bind(R.id.status)
        TextView status;
        public ItemView(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this,view);
            context =view.getContext();
        }

        public void update(final Room data) {
            number.setText(data.getRoomNumber());
            if(data.getReservation() != null) {
                name.setText(data.getReservation().get(0).getGuest().getLastName());
            }
            tiemText.setText(data.getRoomStatus().get(0).getTimestamp());
            status.setText(data.getRoomStatus().get(0).getFOStatus());
            if(data.getRoomStatus().get(0).getFOStatus().equals("OD") || data.getRoomStatus().get(0).getFOStatus().equals("VD") || data.getRoomStatus().get(0).getFOStatus().equals("DND")){
                bar.setImageResource(R.drawable.bar_requirement_red);
                status.setTextColor(context.getResources().getColor(R.color.bar_red));
            }else{
                bar.setImageResource(R.drawable.bar_room_blue);
                status.setTextColor(context.getResources().getColor(R.color.bar_blue));
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getContext().startActivity(new Intent(v.getContext(), RoomDetailActivity.class).putExtra("data",new Gson().toJson(data)));
                }
            });
        }
    }



}