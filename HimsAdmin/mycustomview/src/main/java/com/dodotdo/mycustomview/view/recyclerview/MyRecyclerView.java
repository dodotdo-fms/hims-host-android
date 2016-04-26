package com.dodotdo.mycustomview.view.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import com.dodotdo.mycustomview.R;
import android.support.v7.widget.RecyclerView;

/**
 * Created by BJ on 2015-02-26.
 */
public class MyRecyclerView extends RecyclerView {
    protected View emptyView;

    public MyRecyclerView(Context context) {
        super(context);
    }

    public MyRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setAdapter(final Adapter adapter) {
        super.setAdapter(adapter);
        adapter.registerAdapterDataObserver(new AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                if (emptyView == null) {
                    return;
                }
                if (adapter.getItemCount() == 0) {
                    emptyView.setVisibility(View.VISIBLE);
                } else {
                    emptyView.setVisibility(View.GONE);
                }
            }
        });
        adapter.notifyDataSetChanged();
    }

    public void setEmptyView(View view) {
        emptyView = view;
        Adapter adapter = getAdapter();
        if (adapter == null) {
            emptyView.setVisibility(View.VISIBLE);
        } else {
            if (adapter.getItemCount() == 0) {
                emptyView.setVisibility(View.VISIBLE);
            } else {
                emptyView.setVisibility(View.GONE);
            }
        }
    }
}
