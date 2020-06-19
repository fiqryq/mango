package com.mango.autumnleaves.dosentest.unittest;

import androidx.test.espresso.Espresso;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.dosen.MainDosenActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ShowBapTest {

    @Rule
    public ActivityTestRule<MainDosenActivity> activityTestRule = new ActivityTestRule<>(MainDosenActivity.class);

    @Test
    public void ShowBapTest() throws InterruptedException {
        Thread.sleep(5000);
        Espresso.onView(withId(R.id.bap_menu)).perform(click());
    }
}
