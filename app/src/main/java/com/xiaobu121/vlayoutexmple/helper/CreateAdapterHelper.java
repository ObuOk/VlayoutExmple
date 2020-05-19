package com.xiaobu121.vlayoutexmple.helper;

import android.content.Context;

import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.LayoutHelper;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;

/**
 * author: guojianzhong
 * created on: 2020-05-19 16:05
 * description:
 */
public class CreateAdapterHelper {

    private final Class clz;
    private final Object adapter;
    private final Context context;
    private final LayoutHelper layoutHelper;
    private final DelegateAdapter delegateAdapter;
    private final int itemViewType;
    private OutterClassCreater outterClassCreater;
    private boolean isInnerClass;

    public CreateAdapterHelper(
            Class clz,
            Object adapter,
            Context context,
            LayoutHelper layoutHelper,
            DelegateAdapter delegateAdapter,
            int itemViewType
    ) {
        this.clz = clz;
        this.adapter = adapter;
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.delegateAdapter = delegateAdapter;
        this.itemViewType = itemViewType;
    }

    /**
     * 当adapter 为内部类时，使用这个构造
     * @param clz
     * @param adapter
     * @param context
     * @param layoutHelper
     * @param delegateAdapter
     * @param itemViewType
     * @param outterClassCreater 外部类实例,一般传this即可
     */
    public CreateAdapterHelper(
            Class clz,
            Object adapter,
            Context context,
            LayoutHelper layoutHelper,
            DelegateAdapter delegateAdapter,
            int itemViewType,
            OutterClassCreater outterClassCreater
    ) {
        this.clz = clz;
        this.adapter = adapter;
        this.context = context;
        this.layoutHelper = layoutHelper;
        this.delegateAdapter = delegateAdapter;
        this.itemViewType = itemViewType;
        this.outterClassCreater = outterClassCreater;
    }
    public Object build() {
        if (adapter == null) {
            //视为内部类
            if (clz.getName().contains("$") &&
                    !Modifier.isStatic(clz.getModifiers())) {//非静态
                isInnerClass = true;
                if (outterClassCreater == null) {
                    throw new IllegalArgumentException("adapter 为内部类时, 必须要传入OutterClassCreater");
                }
                return this.build(clz, outterClassCreater.getOutterClass(), context, layoutHelper, delegateAdapter, itemViewType);
            }else {
                return this.build(clz, context, layoutHelper, delegateAdapter, itemViewType);
            }
        }
        return adapter;
    }

    private Object build(Class clz, Object... args) {
        try {
            Constructor[] declaredConstructors = clz.getDeclaredConstructors();
            for (int i = 0; i < declaredConstructors.length; i ++) {
                Constructor declaredConstructor = declaredConstructors[i];
                Class[] parameterTypes = declaredConstructor.getParameterTypes();
                if (parameterTypes.length == args.length) {
                        declaredConstructor.setAccessible(true);
                        return declaredConstructor.newInstance(args);
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public abstract static class OutterClassCreater {
        public abstract Object getOutterClass();
    }

}
