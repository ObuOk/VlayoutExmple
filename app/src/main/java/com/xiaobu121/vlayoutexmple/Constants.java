package com.xiaobu121.vlayoutexmple;

import java.util.ArrayList;

/**
 * author: guojianzhong
 * created on: 2020-05-19 15:01
 * description:
 */
public class Constants {
    private static final ArrayList<String> datas = new ArrayList();

    public static ArrayList<String> getDatas() {
        for (int i = 0; i < 999; i ++) {
            datas.add("这是一条测试数据");
        }
        return datas;
    }
}
