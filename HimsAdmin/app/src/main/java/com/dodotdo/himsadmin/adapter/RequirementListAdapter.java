package com.dodotdo.himsadmin.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.dodotdo.himsadmin.R;
import com.dodotdo.himsadmin.activity.RequirementDetailActivity;
import com.dodotdo.himsadmin.model.Requirement;
import com.dodotdo.mycustomview.view.recyclerview.MyRecyclerView;
import com.dodotdo.mycustomview.view.recyclerview.ViewHolderFactory;
import com.google.gson.Gson;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * Created by BJ on 2015-03-04.
 */
public class RequirementListAdapter extends RecyclerView.Adapter<MyRecyclerView.ViewHolder> {

    public  Context mContext;
    public static final int VIEWTPYE_ITEM = 1;
    private static final int TYPE_FOOTER = 2;

    private boolean footerVisible;

    RequirementListAdapter adapter;
    private List<Requirement> list;
    onItemClickListener listener;

    public void addItem(List<Requirement> list) {
        this.list.addAll(list);
    }

    public void setList(List<Requirement> list) {
        this.list.clear();
        this.list.addAll(list);
    }

    public interface onItemClickListener{
        void onClick(String data);
    }
    public RequirementListAdapter(Context context, List<Requirement> list) {
        mContext = context;
        adapter = this;
        this.list = list;
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

    public List<Requirement> getList() {
        return list;
    }

    public MyRecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view;
        switch (viewType) {
            case VIEWTPYE_ITEM:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_requirement, parent, false);
                return new ItemView(view);
            case TYPE_FOOTER:
                view = LayoutInflater.from(context).inflate(R.layout.viewholder_footer, parent, false);
                return new FooterViewHolder(view);
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


    public void setListener(onItemClickListener listener) {
        this.listener = listener;
    }

    public void changeData(Requirement item) {
        int position = -1;
        for(int i = 0;i<list.size();i++){
            Requirement data = list.get(i);
            if(data.getId() == item.getId()){
                list.add(i,item);
                list.remove(data);
                position = i;
            }
        }
        if(position == -1){
            list.add(0,item);
            position = 0;
        }
        notifyItemChanged(position);

    }


    public  class ItemView extends RecyclerView.ViewHolder {
        Context context;
        View view;
        @Bind(R.id.leftBar)
        ImageView bar;
        @Bind(R.id.title)
        TextView title;
        @Bind(R.id.name)
        TextView name;
        @Bind(R.id.date)
        TextView date;
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

        public void update(final Requirement data) {
            name.setText(data.getSenderEmployee().getLastName());
            date.setText(data.getRegisterTimestamp());
            tiemText.setText("sss");
            status.setText(data.getStatus());
            title.setText(data.getContentEnglish());
            if(data.getStatus().equals("WIP")){
                bar.setImageResource(R.drawable.bar_requirement_red);
                status.setTextColor(context.getResources().getColor(R.color.bar_red));
            }else if(data.getStatus().equals("DONE")){
                bar.setImageResource(R.drawable.bar_requirement_green);
                status.setTextColor(context.getResources().getColor(R.color.bar_green));
            }else{
                bar.setImageResource(R.drawable.bar_requirement_gray);
                status.setTextColor(context.getResources().getColor(R.color.bar_gray));
            }

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(new Gson().toJson(data));
                }
            });
        }
    }
    private class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View footerView) {
            super(footerView);
        }
    }


}