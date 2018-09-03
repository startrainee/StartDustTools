package com.stardust.tools.startdusttools.activity;

import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;

import com.stardust.tools.startdusttools.R;

public class TransitionActivity extends AppCompatActivity {

    TransitionManager transitionManager;
    ViewGroup container;
    Scene scene0;
    Scene scene1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transition);

        /*if(savedInstanceState == null){
            getSupportFragmentManager().beginTransaction().add(R.id.container,new BlankFragment()).commit();
        }*/

        ViewGroup container = findViewById(R.id.container);

        TransitionInflater transitionInflater = TransitionInflater.from(this);
        transitionManager = transitionInflater.inflateTransitionManager(R.transition.transition_manager,container);
        scene0 = Scene.getSceneForLayout(container,R.layout.fragment_transition_before,this);
        scene1 = Scene.getSceneForLayout(container,R.layout.fragment_transition_after,this);
        transitionManager.transitionTo(scene0);
        transitionManager.transitionTo(scene1);

    }

    private void gotoScene(Scene scene){

        ChangeBounds changeBounds = new ChangeBounds();
        changeBounds.setDuration(2000);

        Fade fadeOut = new Fade(Fade.OUT);

        fadeOut.setDuration(2000);
        Fade fadeIn = new Fade(Fade.IN);
        fadeIn.setDuration(2000);

        TransitionSet transitionSet = new TransitionSet();
        transitionSet.setOrdering(TransitionSet.ORDERING_SEQUENTIAL);
        transitionSet
                .addTransition(fadeOut)
                .addTransition(changeBounds)
                .addTransition(fadeIn);
        TransitionManager.go(scene,transitionSet);

    }

    public void startTransition(View view) {
        //gotoScene(scene1);
        //transitionManager.transitionTo(scene0);
    }

    @Override
    public void onBackPressed() {
        //gotoScene(scene0);
        //transitionManager.transitionTo(scene0);

    }
}
