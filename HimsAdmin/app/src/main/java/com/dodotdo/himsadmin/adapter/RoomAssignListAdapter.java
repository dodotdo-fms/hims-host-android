package com.dodotdo.himsadmin.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.model.CheckEmployeeStatus;
import com.dodotdo.himsadmin.model.Room;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BJ on 2015-03-04.
 */
public class RoomAssignListAdapter extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {

    public  Context mContext;
    public static final int VIEWTPYE_ITEM = 1;
    private static final int TYPE_DEFAULT = 0;
    private static int TYPE_CURRENT = 0;
    private static final int TYPE_SEARCH = 1;

    RoomAssignListAdapter adapter;
    private List<CheckRoomData> list;
    private List<CheckRoomData> searchList;
    OnRoomAssignListAdapterListener listener;
    public interface OnRoomAssignListAdapterListener{
        void checkChanged();
    }
    public RoomAssignListAdapter(Context context, List<Room> data) {
        mContext = context;
        adapter = this;
        searchList = new ArrayList<>();
        list = new ArrayList<>();
        setData(data);
    }

    public void setListener(OnRoomAssignListAdapterListener listener) {
        this.listener = listener;
    }

    private void setData(List<Room> data) {
        for(Room room : data){
            if(!room.getRoomStatus().get(0).getFOStatus().equals("VC") || !room.getRoomStatus().get(0).getFOStatus().equals("OC")) {
                list.add(new CheckRoomData(false, room));
            }
        }
    }

    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEWTPYE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_employeelist_member_item, parent, false);
                return new ItemView(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final MyRecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemView) {
            ((ItemView) holder).update(TYPE_CURRENT  == TYPE_DEFAULT ? list.get(position) : searchList.get(position));
        }
    }

    public ArrayList<String > getCheckRoomNumbers(){
        ArrayList<String > result = new ArrayList<>();
        for (CheckRoomData data : list){
            if(data.check){
                result.add(data.room.getRoomNumber());
            }
        }
        return result;
    }

    @Override
    public int getItemCount() {
        return (TYPE_CURRENT  == TYPE_DEFAULT ? list.size() : searchList.size());
    }

    @Override
    public int getItemViewType(int position) {
        return VIEWTPYE_ITEM;
    }

    public void onSearch(String s) {
        searchList.clear();
        if(s.length() == 0){
            TYPE_CURRENT = TYPE_DEFAULT;
            notifyDataSetChanged();
            return;
        }
        TYPE_CURRENT = TYPE_SEARCH;
        for(CheckRoomData item : list){
            if(item.room.getRoomNumber().toLowerCase().startsWith(s)){
                searchList.add(item);
            }
        }
        notifyDataSetChanged();
    }

    public String getSelectedText() {
        int count = 0;
        for(CheckRoomData data : list){
            if(data.check){
                count++;
            }
        }

        if(count != 0){
            return count + " Selected";
        }
        return "";
    }


    public  class ItemView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.check)
        CheckBox check;
        @Bind(R.id.name)
        TextView name;
        public ItemView(View view) {
            super(view);
            this.view = view;
            ButterKnife.bind(this,view);
            context =view.getContext();
        }

        public void update(final CheckRoomData data) {
            if(data.room.getClean() != null && data.room.getClean().size() != 0){
                check.setVisibility(View.INVISIBLE);
            }else{
                check.setVisibility(View.VISIBLE);
            }
            check.setChecked(data.check);
            name.setText(data.room.getRoomNumber());
            check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    data.check = isChecked;
                    listener.checkChanged();
                }
            });
        }
    }

    public class CheckRoomData{
        boolean check;
        Room room;

        public CheckRoomData(boolean check, Room room) {
            this.check = check;
            this.room = room;
        }
    }


}