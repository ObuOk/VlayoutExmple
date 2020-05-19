package com.xiaobu121.vlayoutexmple.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.xiaobu121.vlayoutexmple.helper.AdapterHelper;

import java.util.List;

/**
 * author: guojianzhong
 * created on: 2020-05-13 12:21
 * description:
 */
public abstract class BaseDelegateAdapter<T,
        VH extends BaseDelegateAdapter.BaseViewHolder>
        extends DelegateAdapter.Adapter<VH> {
    public Context mContext;
    public LayoutHelper mLayoutHelper;
    private List<T> datas;
    private int mItemViewType;
    private DelegateAdapter mDelegateAdapter;
    private AdapterHelper mAdapterHelper;
    private static BaseDelegateAdapter baseDelegateAdapter;

    public BaseDelegateAdapter(Context context, LayoutHelper layoutHelper, DelegateAdapter delegateAdapter, int itemViewType) {
        this.mContext = context;
        this.mLayoutHelper = layoutHelper;
        this.mItemViewType = itemViewType;
        this.mDelegateAdapter = delegateAdapter;
        mAdapterHelper = new AdapterHelper(delegateAdapter, this);
    }

    public DelegateAdapter getDelegateAdapter() {
        return mDelegateAdapter;
    }

    public Context getContext() {
        return mContext;
    }

    public LayoutHelper getLayoutHelper() {
        return mLayoutHelper;
    }

    public List<T> getDatas() {
        return datas;
    }

    /**
     * 单一适配器模式
     * @param datas
     * @param singleAdapter
     */
    public void addDatasAndNotify(List<T> datas, boolean singleAdapter) {
        if (!singleAdapter) return;
        if (!mAdapterHelper.hasAdapterInParent()) {
            clear();
            mAdapterHelper.bindAdapter();
        }
        if (this.datas != null) {
            this.datas.addAll(datas);
        }else {
            this.datas = datas;
        }
        notifyDataSetChanged();
    }

    public void addDatasAndNotify(List<T> datas) {
        if (this.datas != null) {
            this.datas.addAll(datas);
        }else {
            this.datas = datas;
        }
        notifyDataSetChanged();
    }

    public void setDatasAndNotify(List<T> datas) {
        this.datas = datas;
        notifyDataSetChanged();
    }
    public void clearDatasAndNotify() {
        if (this.datas != null) {
            this.datas.clear();
            notifyDataSetChanged();
        }
    }
    public void clear() {
        if (this.datas != null) {
            this.datas.clear();
        }
    }

    public void removeDataByObjectAndNotity(T object) {
        if (this.datas != null) {
            this.datas.remove(object);
            notifyDataSetChanged();
        }
    }

    @Override
    public LayoutHelper onCreateLayoutHelper() {
        return mLayoutHelper;
    }

    @NonNull
    @Override
    public VH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return doCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(@NonNull VH holder, int position) {
        doBindViewHolder(holder, position);
    }

    @Override
    public int getItemCount() {
        return datas == null ? 0 : datas.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mItemViewType;
    }

    public abstract void doBindViewHolder(VH holder, int position);

    public abstract VH doCreateViewHolder(ViewGroup parent, int viewType);


    /************************* 所有viewHolder 继承这个base ****************************/
    public static class BaseViewHolder
            extends RecyclerView.ViewHolder {
        public BaseViewHolder(View itemView) {
            super(itemView);
            logic();
        }
        public void logic() {


        }

    }

}
