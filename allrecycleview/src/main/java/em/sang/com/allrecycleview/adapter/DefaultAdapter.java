package em.sang.com.allrecycleview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.CustomPeakHolder;
import em.sang.com.allrecycleview.inter.DefaultAdapterViewListener;


/**
 * Description：带有头布局的Adapter
 *
 * @Author：桑小年
 * @Data：2016/11/8 9:50
 */
public class DefaultAdapter<T> extends CustomBasicAdapter<T> {


    public DefaultAdapter(Context context, List<T> lists, int itemID, DefaultAdapterViewListener<T> listener) {
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
            ((CustomPeakHolder) holder).initView(id, context);
        } else if (position < getHeaderSize() + tops.size()) {
            id = position - tops.size();
            ((CustomPeakHolder) holder).initView(id, context);
        } else if (position < getHeaderSize() + getBodySize() + tops.size()) {
            id = position - getHeaderSize() - tops.size();
            ((CustomHolder) holder).initView(id,lists, context);
        } else if (position < getHeaderSize() + getBodySize() + tops.size() + getFooterSize()){
            id = position - getHeaderSize() - tops.size() - getBodySize();
            ((CustomPeakHolder) holder).initView(id, context);
        }else {
            id = position - (getHeaderSize() + getBodySize() + tops.size() + getFooterSize());
            ((CustomPeakHolder) holder).initView(id, context);
        }


    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        if (viewType == TOP) {
            holder = (RecyclerView.ViewHolder) tops.get(0);
        } else if (viewType < getHeaderSize() + tops.size()) {
            holder = (RecyclerView.ViewHolder) headers.get(viewType - tops.size());
        } else if (viewType == BODY) {
            holder = ((DefaultAdapterViewListener<T>) listener).getBodyHolder(context, lists, itemID);
        } else if (viewType < getHeaderSize() + getBodySize() + tops.size() + getFooterSize()){
            holder = (RecyclerView.ViewHolder) foots.get(viewType - getHeaderSize() - getBodySize() - tops.size());
        }else if (viewType == FOOT){
            holder = (RecyclerView.ViewHolder) booms.get(0);
        }
        return holder;
    }
}
