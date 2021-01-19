package cn.edsmall.skudemo.skuadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Created by LZH on 2018/6/1.
 */
public abstract class BaseRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int BLANK = 100001, FOOTER = 100002, NORMAL = 100003, HEADER = 100004;
    private View blankView, footerView, headerView;
    private int itemCount = 0;
    private int count = 0;
    private OnBottomListener onBottomListener;
    private OnHeaderListener onHeaderListener;
    private View.OnClickListener onItemClickListener;
    protected Context mContext;

    public BaseRVAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public BaseRVAdapter(Context mContext, View blankView, View footerView, View headerView) {
        this.mContext = mContext;
        this.blankView = blankView;
        this.footerView = footerView;
        this.headerView = headerView;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;
        if (viewType == HEADER) {
            holder = new HeaderViewHolder(headerView);
        } else if (viewType == FOOTER) {
            ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            footerView.setLayoutParams(lp);
            holder = new FooterViewHolder(footerView);
        } else if (viewType == BLANK) {
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            blankView.setLayoutParams(lp);
            holder = new BlankViewHolder(blankView);
        } else {
            holder = createVH(parent, viewType);
            holder.itemView.setOnClickListener(onItemClickListener);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position, List<Object> payloads) {
        if (position == 0 && onHeaderListener != null) {
            holder.itemView.setOnClickListener(v -> onHeaderListener.onHeaderListener());
        }
        if (position > (getItemCount() - 1) / 2 && onBottomListener != null) {
            onBottomListener.onBottomListener();
        }
        if (headerView != null) {
            position -= 1;
        }
        super.onBindViewHolder(holder, position, payloads);
    }

    @Override
    public int getItemCount() {
        count = itemCount();
        if (count == 0 && blankView != null) {
            return 1;
        } else {
            return itemCount + count;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (count == 0 && blankView != null) return BLANK;
        if (position == 0 && headerView != null) return HEADER;
        if (position == (getItemCount() - 1) && footerView != null) return FOOTER;
        return NORMAL;
    }

    public void setBlankView(View blankView) {
        this.blankView = blankView;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
        if (this.footerView != null) {
            itemCount++;
        }
    }

    public void setHeaderView(View headerView) {
        this.headerView = headerView;
        if (this.headerView != null) {
            itemCount++;
        }
    }

    public void setOnBottomListener(OnBottomListener onBottomListener) {
        this.onBottomListener = onBottomListener;
    }

    public void setOnHeaderListener(OnHeaderListener onHeaderListener) {
        this.onHeaderListener = onHeaderListener;
    }

    public void setOnItemClickListener(View.OnClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public class FooterViewHolder extends RecyclerView.ViewHolder {
        public FooterViewHolder(View itemView) {
            super(itemView);
        }
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        public HeaderViewHolder(View itemView) {
            super(itemView);
        }
    }

    class BlankViewHolder extends RecyclerView.ViewHolder {
        public BlankViewHolder(View itemView) {
            super(itemView);
        }
    }

    public abstract RecyclerView.ViewHolder createVH(ViewGroup parent, int viewType);

    public abstract int itemCount();

    public interface OnBottomListener {
        void onBottomListener();
    }

    public interface OnHeaderListener {
        void onHeaderListener();
    }
}
