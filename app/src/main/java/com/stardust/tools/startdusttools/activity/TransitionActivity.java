package com.stardust.tools.startdusttools.activity;

import android.os.Bundle;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.TransitionInflater;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.stardust.tools.startdusttools.R;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class TransitionActivity extends AppCompatActivity {

    TransitionManager transitionManager;
    ViewGroup container;
    Scene scene0;
    Scene scene1;
    Scene scene2;
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
        scene2 = Scene.getSceneForLayout(container,R.layout.fragment_transition_before_2,this);
        transitionManager.transitionTo(scene0);
        ImageView imageView = findViewById(R.id.image_view);
        imageView.setImageResource(R.drawable.ic_mic_black_24dp);


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
        transitionManager.transitionTo(scene2);

        Class viewClass = view.getClass();
        Log.d("wcy","------------------ method.length:method.length: "  + viewClass.getMethods().length);
        Log.d("wcy","------------------ method.length: "  + viewClass.getFields().length);


        try {
            Method method = viewClass.getMethod("setText",CharSequence.class);
            method.invoke(view,"刘德华");
            Log.d("wcy","textView.text : "  + ((TextView)view).getText());
        } catch (IllegalAccessException e) {
                e.printStackTrace();
                Log.e("wcy",e.getMessage());
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            Log.e("wcy",e.getMessage());
            } catch (NoSuchMethodException e) {
            e.printStackTrace();
            Log.e("wcy",e.getMessage());
        }
    }

    @Override
    public void onBackPressed() {
        //gotoScene(scene0);
        //transitionManager.transitionTo(scene0);
        super.onBackPressed();

    }
}
