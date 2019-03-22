package com.stardust.tools.startdusttools.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.stardust.tools.startdusttools.R;
import com.stardust.tools.startdusttools.activity.recycleview.RecycleViewActivity;

public class MainActivity extends AppCompatActivity {
    String TAG = "MainActivity";
    LockScreenMsgReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        receiver = new LockScreenMsgReceiver();
        registerScreenActionReceiver();
    }

    //poi搜索界面
    public void startMainActivity(View view) {
        startActivity(new Intent(MainActivity.this, PoiSearchActivity.class));
    }

    //动画相关界面
    public void startAnimationActivity(@NonNull View view) {
        startActivity(new Intent(MainActivity.this, AnimationActivity.class));
    }

    //过渡动画界面
    public void startTransitionActivity(View view) {
        startActivity(new Intent(MainActivity.this, TransitionActivity.class));
    }

    //数据绑定界面
    public void startDataBindingActivity(View view) {
        startActivity(new Intent(MainActivity.this, BindingActivity.class));
    }

    //高德定位界面
    public void startLocationActivity(View view) {
        startActivity(new Intent(MainActivity.this, LocationActivity.class));
    }

    //高德定位界面
    public void startRecycleViewActivity(View view) {
        startActivity(new Intent(MainActivity.this, RecycleViewActivity.class));
    }

    private void registerScreenActionReceiver() {
        Log.i(TAG, "registerScreenActionReceiver() ");
        final IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_SCREEN_OFF);
        filter.addAction(Intent.ACTION_SCREEN_ON);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}

class LockScreenMsgReceiver extends BroadcastReceiver {
    private static final String TAG = "LockScreenMsgReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i(TAG, "onReceive:收到了锁屏消息 ");
        String action = intent.getAction();
        Log.d(TAG, "action: " + action);
        if (Intent.ACTION_SCREEN_ON.equals(action)) {
            Log.i(TAG, "onReceive:解锁了 ");
            //判断是否锁屏
            Intent alarmIntent = new Intent(context, LockScreenActivity.class);
            //在广播中启动Activity的context可能不是Activity对象，所以需要添加NEW_TASK的标志，否则启动时可能会报错。
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(alarmIntent); //启动显示锁屏消息的activity
        }
    }
}
