package em.sang.com.allrecycleview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.List;

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
public class RefrushAdapter<T> extends DefaultAdapter<T> {

    protected int refrushPosition=0;

    public RefrushAdapter(Context context, List lists, int itemID, DefaultAdapterViewListener listener) {
        super(context, lists, itemID, listener);
    }

    public void setRefrushPosition(int position){
        this.refrushPosition=position;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == refrushPosition&&tops.size()>0) {
            return TOP;
        } else if (position < getHeaderSize()+tops.size()) {
            if (position<refrushPosition) {
                return position;
            }else if (position>refrushPosition&&tops.size()>0){
                return position;
            }else {
                return position;
            }
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

        int id ;
        if (position == refrushPosition&&tops.size()>0) {
            id=position;
            ((CustomPeakHolder) holder).initView(id, context);
        } else if (position < getHeaderSize()+tops.size()) {
            id= position-tops.size();
            if (position<refrushPosition) {
                id= position;
            }else if (position>refrushPosition&&tops.size()>0){
                id= position-1;
            }
            ((CustomPeakHolder) holder).initView(id, context);
        } else if (position < getHeaderSize() + getBodySize() + tops.size()) {
            id=position- getHeaderSize()-tops.size();
            ((CustomHolder) holder).initView(id,lists, context);
        } else if (position< getHeaderSize() + getBodySize() + tops.size()+getFooterSize()){
            id = position- getHeaderSize()-tops.size()-getBodySize();
            ((CustomPeakHolder) holder).initView(id, context);
        }else {
            id = position-(getHeaderSize() + getBodySize() + tops.size()+getFooterSize());
            ((CustomPeakHolder) holder).initView(id, context);
        }


    }



    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        RecyclerView.ViewHolder holder = null;

        if (viewType == TOP) {
            holder = (RecyclerView.ViewHolder) tops.get(0);
        } else if (viewType < getHeaderSize()+tops.size()) {
            if (viewType>refrushPosition&&tops.size()>0) {
                holder = (RecyclerView.ViewHolder) headers.get(viewType - tops.size());
            }else {
                holder = (RecyclerView.ViewHolder) headers.get(viewType);
            }
        } else if (viewType == BODY) {
            holder= ((DefaultAdapterViewListener) listener).getBodyHolder(context, lists, itemID);
        } else if (viewType< getHeaderSize() + getBodySize() + tops.size()+getFooterSize()){
            holder= (RecyclerView.ViewHolder) foots.get(viewType - getHeaderSize() - getBodySize()-tops.size());
        }else if (viewType==FOOT){
            holder = (RecyclerView.ViewHolder) booms.get(0);
        }
        return holder;
    }




}
