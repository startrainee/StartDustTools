package com.stardust.tools.startdusttools;

import android.content.Intent;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.stardust.tools.startdusttools.activity.AnimationActivity;
import com.stardust.tools.startdusttools.activity.BindingActivity;
import com.stardust.tools.startdusttools.activity.MainActivity;
import com.stardust.tools.startdusttools.activity.TransitionActivity;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    public void startMainActivity(View view) {
        startActivity(new Intent(HomeActivity.this,MainActivity.class));
    }
    public void startAnimationActivity(@NonNull View view) {
        startActivity(new Intent(HomeActivity.this,AnimationActivity.class));
    }

    public void startTransitionActivity(View view) {
        startActivity(new Intent(HomeActivity.this,TransitionActivity.class));
    }

    public void startDataBindingActivity(View view) {
        startActivity(new Intent(HomeActivity.this,BindingActivity.class));
    }
}
