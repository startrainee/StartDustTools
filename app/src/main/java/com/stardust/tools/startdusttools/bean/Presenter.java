package com.stardust.tools.startdusttools.bean;

import android.util.Log;

public class Presenter {

    public void changeName(DataBean dataBean) {
        Log.d("wcy","changeName（）： " + dataBean.getType());
        dataBean.setName("修改之前" + dataBean.getType());
        int type = dataBean.getType() + 111111;
        dataBean.setType(type);

    }

}