package com.stardust.tools.startdusttools.bean;

import android.databinding.BaseObservable;

/**
 * Created on 2018/9/6.
 *
 * @author siasun-wangchongyang
 */
public class DataBean extends BaseObservable{

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
        notifyChange();
    }


    private String name;
    private int type;
}
