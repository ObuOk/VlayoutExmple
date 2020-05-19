package com.xiaobu121.vlayoutexmple;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.LinearLayoutHelper;
import com.xiaobu121.vlayoutexmple.adapter.BaseDelegateAdapter;
import com.xiaobu121.vlayoutexmple.helper.CreateAdapterHelper;

import java.util.List;

/**
 * author: guojianzhong
 * created on: 2020-04-23 16:33
 * description:
 */
public class Example1 extends AppCompatActivity {

    private RecyclerView mRecycler_view;
    private SubAdapter mSubAdapter;
    private DelegateAdapter mDelegateAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecycler_view = findViewById(R.id.recycler_view);
        setView();
    }

    private void setView() {
        VirtualLayoutManager virtualLayoutManager = new VirtualLayoutManager(this);
        mDelegateAdapter = new DelegateAdapter(virtualLayoutManager);
        mRecycler_view.setLayoutManager(virtualLayoutManager);
        mRecycler_view.setAdapter(mDelegateAdapter);
        mSubAdapter = (SubAdapter) new CreateAdapterHelper(
                Example1.SubAdapter.class,
                mSubAdapter, this,
                new LinearLayoutHelper(10),
                mDelegateAdapter,
                1,
                new CreateAdapterHelper.OutterClassCreater(){
            @Override
            public Object getOutterClass() {
                return Example1.this;
            }
        } ).build();
        mSubAdapter.addDatasAndNotify(Constants.getDatas(), true);
    }


       static class SubAdapter extends BaseDelegateAdapter<String, BaseDelegateAdapter.BaseViewHolder> {


        private SubAdapter(Context context, LayoutHelper layoutHelper, DelegateAdapter delegateAdapter, int itemViewType) {
            super(context, layoutHelper, delegateAdapter, itemViewType);
        }

        @Override
        public void doBindViewHolder(BaseViewHolder holder, int position) {
            ((TextView)holder.itemView).setText(getDatas().get(position));
        }

        @Override
        public BaseViewHolder doCreateViewHolder(ViewGroup parent, int viewType) {
            TextView textView = new TextView(mContext);
            textView.setGravity(Gravity.CENTER);
            textView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            return new ViewHolder(textView);
        }
    }


    static class ViewHolder extends BaseDelegateAdapter.BaseViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }


}
