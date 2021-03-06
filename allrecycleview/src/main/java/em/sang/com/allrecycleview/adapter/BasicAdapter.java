package em.sang.com.allrecycleview.adapter;

import android.content.Context;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import em.sang.com.allrecycleview.holder.CustomHolder;
import em.sang.com.allrecycleview.holder.CustomPeakHolder;
import em.sang.com.allrecycleview.inter.CustomAdapterListener;


/**
 * Description：
 * <p>
 * Author： 桑小年
 * <p>
 * Data： 2016/11/7 16:43
 */
public class BasicAdapter<T> extends RecyclerView.Adapter {
    protected List<T> lists = new ArrayList<>();
    public int itemID;
    protected List<CustomPeakHolder> headers = new ArrayList<>();
    protected List<CustomPeakHolder> foots = new ArrayList<>();
    protected List<CustomPeakHolder> tops = new ArrayList<>();
    protected List<CustomPeakHolder> booms = new ArrayList<>();
    public CustomAdapterListener<T> listener;

    public Context context;

    public static final int BODY = 100000;
    public static final int TOP = 100001;
    public static final int FOOT = 100002;

    private GridLayoutManager.SpanSizeLookup mSpanSizeLookup;


    protected BasicAdapter(Context context, List<T> lists, int itemID, CustomAdapterListener<T> listener) {
        if (lists != null) {
            this.lists = lists;
        }
        this.itemID = itemID;
        this.listener = listener;
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (listener != null) {
            return listener.getItemTypeByPosition();
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CustomHolder holder = null;
        if (listener != null) {
            holder = listener.getHolderByViewType(context, lists, viewType);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (listener != null) {
            listener.onBindViewHolder(holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return getBodySize() + getHeaderSize() + getFooterSize() + tops.size() + booms.size();
    }

    public int getBodySize(){
        return lists == null ? 0 : lists.size();
    }

    public int getHeaderSize(){
        return headers == null ? 0 : headers.size();
    }

    public int getFooterSize(){
        return foots == null ? 0 : foots.size();
    }

    /**
     * 添加头布局
     *
     * @param heardHolder 头布局的holder
     */
    public void addHead(CustomPeakHolder heardHolder) {
        headers.add(heardHolder);
    }

    /**
     * 添加头布局
     *
     * @param heardHolder 头布局的holder
     */
    public void addHead(int index, CustomPeakHolder heardHolder) {

        headers.add(index, heardHolder);
    }

    /**
     * 添加脚布局
     *
     * @param footHolder
     */
    public void addFoots(CustomPeakHolder footHolder) {
        foots.add(footHolder);
    }

    /**
     * 添加顶部刷新局
     *
     * @param topHolder
     */
    public void addTop(CustomPeakHolder topHolder) {
        tops.clear();
        tops.add(topHolder);
    }



    /**
     * 添加顶部刷新局
     *
     * @param boomHolder
     */
    public void addBoom(CustomPeakHolder boomHolder) {
        booms.clear();
        booms.add(boomHolder);
    }



    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            mSpanSizeLookup = gridManager.getSpanSizeLookup();
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    return (isHeader(position) || isFooter(position))
                            ? gridManager.getSpanCount() : (null == mSpanSizeLookup ? 1 : mSpanSizeLookup.getSpanSize(position));
                }


            });
        }
    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        super.onViewAttachedToWindow(holder);
        ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
        if (lp != null
                && lp instanceof StaggeredGridLayoutManager.LayoutParams
                && (isHeader(holder.getLayoutPosition()) || isFooter(holder.getLayoutPosition()))) {
            StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) lp;
            p.setFullSpan(true);
        }
    }


    public boolean isFooter(int position) {
        return position >= getHeaderSize() + getBodySize() + tops.size() && position < tops.size() + getHeaderSize() + getBodySize() + getFooterSize() + booms.size();
    }

    public boolean isHeader(int position) {
        return position >= 0 && position < getHeaderSize() + tops.size();
    }

    public List<T> getBodyLists() {
        return lists;
    }

    public void upData(List<T> list) {
        this.lists = list;
        notifyDataSetChanged();

    }

    public List<CustomPeakHolder> getHeaders(){
        return headers;
    }

    public List<CustomPeakHolder> getFoots() {
        return foots;
    }

    public List<CustomPeakHolder> getTops() {
        return tops;
    }

    public List<CustomPeakHolder> getBooms() {
        return booms;
    }
}
