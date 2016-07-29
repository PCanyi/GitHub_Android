package com.zpauly.githubapp.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zpauly.githubapp.R;
import com.zpauly.githubapp.view.viewholder.LoadMoreViewHolder;

/**
 * Created by zpauly on 16-7-28.
 */

public abstract class LoadMoreRecyclerViewAdapter<VH extends RecyclerView.ViewHolder>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final String TAG = getClass().getName();

    public static final int LOAD_MORE_VIEW_TYPE = 10000;

    protected Context mContext;

    private boolean hasMoreData = true;

    protected LoadMoreRecyclerViewAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == LOAD_MORE_VIEW_TYPE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_loadmore, parent, false);
            return new LoadMoreViewHolder(view);
        } else {
            return createContentViewHolder(parent, viewType);
        }
    }

    public abstract VH createContentViewHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position == getItemCount() - 1) {
            LoadMoreViewHolder loadMoreViewHolder = (LoadMoreViewHolder) holder;
            loadMoreViewHolder.mLoadPB.setVisibility(View.GONE);
            loadMoreViewHolder.mLoadTV.setVisibility(View.VISIBLE);
            if (hasMoreData) {
                loadMoreViewHolder.mLoadPB.setVisibility(View.VISIBLE);
                loadMoreViewHolder.mLoadTV.setVisibility(View.GONE);
            } else {
                loadMoreViewHolder.mLoadPB.setVisibility(View.GONE);
                loadMoreViewHolder.mLoadTV.setVisibility(View.VISIBLE);
            }
        } else {
            VH viewholder = (VH) holder;
            bindContentViewHolder(viewholder, position);
        }
    }

    public abstract void bindContentViewHolder(VH holder, int position);

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return LOAD_MORE_VIEW_TYPE;
        }
        return super.getItemViewType(position);
    }

    public void setHasLoading(boolean hasMoreData) {
        this.hasMoreData = hasMoreData;
        notifyDataSetChanged();
    }

    public boolean isHasMoreData() {
        return hasMoreData;
    }
}
