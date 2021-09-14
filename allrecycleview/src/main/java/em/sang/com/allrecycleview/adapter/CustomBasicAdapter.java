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
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/7 16:43
 */
public class CustomBasicAdapter<T> extends BasicAdapter<T> {
    protected CustomBasicAdapter(Context context, List<T> lists, int itemID, DefaultAdapterViewListener<T> listener) {
        super(context, lists, itemID, listener);
    }

    @Override
    public int getItemViewType(int position) {
        if (listener != null) {
            return listener.getItemTypeByPosition();
        }
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomHolder holder = null;
        if (listener!=null) {
            holder   = listener.getHolderByViewType(context, lists, itemID);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (position < getHeaderSize()) {
            ((CustomHolder) holder).initView(position,lists,context);

        } else if (position < getHeaderSize() + getBodySize()) {
            ((CustomPeakHolder) holder).initView(position - getHeaderSize(),context);

        } else {
            ((CustomPeakHolder) holder).initView(position - getHeaderSize() - getBodySize(),context);
        }

    }
}
