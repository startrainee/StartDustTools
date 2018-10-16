package com.stardust.tools.startdusttools;

import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.stardust.tools.startdusttools.activity.PoiSearchActivity;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

    private PoiSearchActivity mMainAcvitiy;

    @Rule
    public ActivityTestRule<PoiSearchActivity> mActivityRule = new ActivityTestRule<>(PoiSearchActivity.class);

    @Before
    public void setup() {
        mMainAcvitiy = mActivityRule.getActivity();
    }

    @Test
    public void text(){
        useAppContext();
        useAppCont();
    }

    public void useAppCont() {
        // Context of the app under test.
        onView(withId(R.id.floating_search_view)).check(matches(withText("我的世界")));
    }

    public void useAppContext() {
        // Context of the app under test.
        //assertEquals("com.stardust.tools.startdusttools", appContext.getPackageName());
        onView(withId(R.id.floating_search_view)).check(matches(withText("Hello World!")));
        onView(withText("STARTDUSTTOOLS")).perform(click());
        onView(withId(R.id.floating_search_view)).check(matches(withText("我的世界")));
    }
}
