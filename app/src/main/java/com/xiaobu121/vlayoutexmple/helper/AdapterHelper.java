package com.xiaobu121.vlayoutexmple.helper;

import android.util.Pair;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.xiaobu121.vlayoutexmple.adapter.BaseDelegateAdapter;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * author: guojianzhong
 * created on: 2020-05-19 12:29
 * description:
 */
public class AdapterHelper {

    private DelegateAdapter mDelegateAdapter;
    private BaseDelegateAdapter mBaseDelegateAdapter;

    public AdapterHelper(DelegateAdapter mDelegateAdapter, BaseDelegateAdapter adapter) {
        this.mDelegateAdapter = mDelegateAdapter;
        this.mBaseDelegateAdapter = adapter;
    }


    /**
     * 判断DelegateAdapter 包含当前的subAdapter
     * @return
     */
    public boolean hasAdapterInParent() {
        if (mDelegateAdapter == null || mBaseDelegateAdapter == null) {
            throw new IllegalArgumentException("请传递完整的参数");
        }
        try {
            Field adaptersField = mDelegateAdapter.getClass().getDeclaredField("mAdapters");
            adaptersField.setAccessible(true);
            Object o = adaptersField.get(mDelegateAdapter);
            if (o instanceof ArrayList) {
                for (Object pair :
                        (ArrayList)o) {
                    if (pair instanceof Pair) {
                        Object second = ((Pair) pair).second;
                        if (second instanceof DelegateAdapter.Adapter) {
                            if (second.equals(mBaseDelegateAdapter)) {
                                return true;
                            }
                        }
                    }
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void bindAdapter() {
        if (mDelegateAdapter == null || mBaseDelegateAdapter == null) {
            throw new IllegalArgumentException("请传递完整的参数");
        }
        mDelegateAdapter.addAdapter(mBaseDelegateAdapter);
    }

}
