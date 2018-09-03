package com.stardust.tools.startdusttools.activity;

import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

import com.stardust.tools.startdusttools.R;

public class AnimationActivity extends AppCompatActivity {

    Drawable frameAnimationDrawable;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        imageView = findViewById(R.id.image_view_animation);

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    public void startFrameAnimation(View view) {

        if (frameAnimationDrawable == null) {
            frameAnimationDrawable = ContextCompat.getDrawable(this, R.drawable.frame_animation);
        }
        imageView.setBackground(frameAnimationDrawable);

        AnimationDrawable frameAnimate = (AnimationDrawable) imageView.getBackground();

        if (frameAnimate.isRunning()) {
            frameAnimate.stop();
            return;
        }

        frameAnimate.start();
    }


}
