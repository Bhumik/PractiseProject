package com.bhumik.practiseproject.views.universalList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by bhumik on 17/5/16.
 * Source : http://blog.csdn.net/tianzhaoai/article/details/50371026
 */

public abstract class CommonAdapter<T> extends BaseAdapter {
    protected LayoutInflater mInflater;
    protected Context mContext;
    protected List<T> mDatas;
    protected int mItemLayoutId;

    public CommonAdapter(Context context, List<T> mDatas, int itemLayoutId) {
        this.mContext = context;
        this.mInflater = LayoutInflater.from(mContext);
        this.mDatas = mDatas;
        this.mItemLayoutId = itemLayoutId;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public T getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final UniversalViewHolder universalViewHolder = getViewHolder(position, convertView, parent);
        convert(universalViewHolder, getItem(position));
        return universalViewHolder.getConvertView();

    }

    public abstract void convert(UniversalViewHolder Helper, T Item);

    public void setDatas(List<T> mDatas) {
        this.mDatas = mDatas;
        notifyDataSetChanged();
    }

    private UniversalViewHolder getViewHolder(int position, View convertView, ViewGroup parent) {
        return UniversalViewHolder.get(mContext, convertView, parent, mItemLayoutId, position);
    }

}
