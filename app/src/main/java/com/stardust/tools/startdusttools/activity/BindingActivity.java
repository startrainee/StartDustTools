package com.stardust.tools.startdusttools.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.orhanobut.logger.Logger;
import com.stardust.tools.startdusttools.R;
import com.stardust.tools.startdusttools.bean.DataBean;
import com.stardust.tools.startdusttools.bean.Presenter;
import com.stardust.tools.startdusttools.databinding.ActivityBindingBinding;

public class BindingActivity extends AppCompatActivity {
    DataBean dataBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityBindingBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_binding);
        binding.setPresenter(new Presenter());
        dataBean = new DataBean();
        dataBean.setType(111111);
        dataBean.setName("测试之前: "  + dataBean.getType());
        binding.setDataBean(dataBean);
    }

    public void changeName(View view) {
        Logger.d("wcy","changeName（）： " + dataBean.getType());
        dataBean.setName("修改asdfasdfasdf之前" + dataBean.getType());
        int type = dataBean.getType() + 111111;
        dataBean.setType(type);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
