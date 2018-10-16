package com.stardust.tools.startdusttools.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stardust.tools.startdusttools.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }
    //poi搜索界面
    public void startMainActivity(View view) {
        startActivity(new Intent(MainActivity.this,PoiSearchActivity.class));
    }
    //动画相关界面
    public void startAnimationActivity(@NonNull View view) {
        startActivity(new Intent(MainActivity.this,AnimationActivity.class));
    }
    //过渡动画界面
    public void startTransitionActivity(View view) {
        startActivity(new Intent(MainActivity.this,TransitionActivity.class));
    }
    //数据绑定界面
    public void startDataBindingActivity(View view) {
        startActivity(new Intent(MainActivity.this,BindingActivity.class));
    }
}
