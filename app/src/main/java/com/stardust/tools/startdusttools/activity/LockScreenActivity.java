package com.stardust.tools.startdusttools.activity;

import android.app.WallpaperManager;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.stardust.tools.startdusttools.R;

public class LockScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("tag", "onCreate:启动了消息内容的activity ");
        //四个标志位顾名思义，分别是锁屏状态下显示，解锁，保持屏幕长亮，打开屏幕。这样当Activity启动的时候，它会解锁并亮屏显示。
        Window win = getWindow();
        win.addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED //锁屏状态下显示
                | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD //解锁
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON //保持屏幕长亮
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON); //打开屏幕
        Drawable wallPaper = WallpaperManager.getInstance( this).getDrawable();
        win.setBackgroundDrawable(wallPaper);
        setContentView(R.layout.activity_lock_screen);
    }
}