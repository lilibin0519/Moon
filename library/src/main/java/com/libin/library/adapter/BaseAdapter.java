package com.libin.library.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.libin.library.holder.BaseHolder;

import java.util.List;

import em.sang.com.allrecycleview.adapter.DefaultAdapter;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.CustomPeakHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewListener;

/**
 * <p>Description: </p>
 *
 * @author lilibin
 * @date 2021/8/14
 */
public class BaseAdapter<T> extends DefaultAdapter<T> {

    public BaseAdapter(Context context, List<T> lists, int itemID, DefaultAdapterViewListener<T> listener) {
        super(context, lists, itemID, listener);
    }

    @Override
    public int getItemViewType(int position) {
        if (position < tops.size()) {
            return TOP;
        } else if (position < getHeaderSize()+tops.size()) {
            return position;
        } else if (position < getHeaderSize() + getBodySize() + tops.size()) {
            return BODY;
        } else if (position< getHeaderSize() + getBodySize() + tops.size()+getFooterSize()){
            return position;
        }else {
            return FOOT;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int id;
        if (position < tops.size()) {
            id = position;
            ((CustomPeakHolder<?>) holder).initView(id, context);
        } else if (position < getHeaderSize() + tops.size()) {
            id = position - tops.size();
            ((CustomPeakHolder<?>) holder).initView(id, context);
        } else if (position < getHeaderSize() + getBodySize() + tops.size()) {
            id = position - getHeaderSize() - tops.size();
            ((BaseHolder) holder).initView(id,lists, context);
        } else if (position < getHeaderSize() + getBodySize() + tops.size() + getFooterSize()){
            id = position - getHeaderSize() - tops.size() - getBodySize();
            ((CustomPeakHolder<?>) holder).initView(id, context);
        }else {
            id = position - (getHeaderSize() + getBodySize() + tops.size() + getFooterSize());
            ((CustomPeakHolder<?>) holder).initView(id, context);
        }


    }


    @NonNull
    @Override
    public CustomHolder<?> onCreateViewHolder(ViewGroup parent, int viewType) {

        CustomHolder<?> holder;

        if (viewType == TOP) {
            holder = (CustomPeakHolder<?>) tops.get(0);
        } else if (viewType < getHeaderSize() + tops.size()) {
            holder = (CustomPeakHolder<?>) headers.get(viewType - tops.size());
        } else if (viewType == BODY) {
            holder = ((BaseAdapterViewListener<T, ? extends ViewBinding>) listener).getBodyHolder(context, lists, parent);
        } else if (viewType < getHeaderSize() + getBodySize() + tops.size() + getFooterSize()){
            holder = (CustomPeakHolder<?>) foots.get(viewType - getHeaderSize() - getBodySize() - tops.size());
        }else if (viewType == FOOT){
            holder = (CustomPeakHolder<?>) booms.get(0);
        } else {
            holder = new CustomHolder<>(context, lists, 0);
        }
        return holder;
    }
}
