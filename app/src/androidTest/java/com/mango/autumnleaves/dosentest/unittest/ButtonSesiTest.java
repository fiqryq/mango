package com.mango.autumnleaves.dosentest.unittest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.dosen.MainDosenActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.action.ViewActions.swipeRight;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)

public class ButtonSesiTest {
    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<MainDosenActivity> activityTestRule = new ActivityTestRule<>(MainDosenActivity.class);

    @Test
    public void ButtonSesi() throws InterruptedException {
        Thread.sleep(5000);
        Espresso.onView(withId(R.id.constraintKelas)).perform(click());
        Espresso.onView(withId(R.id.ButtonSwitch)).perform(swipeRight());
        Thread.sleep(3000);
        Espresso.onView(withId(R.id.ButtonSwitch)).perform(swipeLeft());
    }
}
