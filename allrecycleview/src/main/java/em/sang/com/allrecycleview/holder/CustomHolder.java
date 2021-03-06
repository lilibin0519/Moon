package em.sang.com.allrecycleview.holder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import java.util.List;

import androidx.recyclerview.widget.RecyclerView;
import em.sang.com.allrecycleview.listener.OnToolsItemClickListener;
import em.sang.com.allrecycleview.utils.ViewHolderHelper;

/**
 * Description：
 *
 * @Author：桑小年
 * @Data：2016/11/7 16:46
 */
public  class CustomHolder<T> extends RecyclerView.ViewHolder {


    public Context context;
    public View itemView;
    protected List<T> data;
    protected int itemID;
    public OnToolsItemClickListener<T> listener;

    protected ViewHolderHelper holderHelper;

    public void setOnTOnToolsItemClickListener(OnToolsItemClickListener<T> listener){
        this.listener=listener;
    }


    public CustomHolder(View itemView) {
        super(itemView);
        holderHelper = new ViewHolderHelper(itemView);

    }

    public CustomHolder(List<T> data, View itemView ){
        this(itemView);
        this.data = data;
        this.itemView = itemView;

    }

    public CustomHolder(Context context, List<T> lists, int itemID) {
        this(lists,LayoutInflater.from(context).inflate(itemID,null));
        this.context =context;
        this.itemID=itemID;
    }

    /**
     * body中使用的初始化方法
     * @param position
     * @param data
     * @param context
     */
    public void initView(int position,List<T> data,Context context) {

    }




    /**
     * 获取跟布局
     * @return
     */
    public View getItemView(){
        return  itemView;
    }

    public Context getContext() {
        return context;
    }

    public List<T> getData() {
        return data;
    }

    public int getItemID() {
        return itemID;
    }
}
