package com.mango.autumnleaves.dosentest.unittest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.dosen.MainDosenActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ButtonSubmitTest {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<MainDosenActivity> activityTestRule = new ActivityTestRule<>(MainDosenActivity.class);

    @Test
    public void TestSubmitBap() throws InterruptedException {
        Thread.sleep(5000);
        Espresso.onView(withId(R.id.constraintKelasTiga)).perform(click());
        Espresso.onView(withId(R.id.btnSubmit)).perform(click());
        Thread.sleep(1000);
        onView(withText("Submit")).perform(click()).check(myIsDisplayed());
    }
}
