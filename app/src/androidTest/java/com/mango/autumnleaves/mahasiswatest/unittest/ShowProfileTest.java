package com.mango.autumnleaves.mahasiswatest.unittest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ShowProfileTest {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void showHistory() throws InterruptedException {

        Espresso.onView(withId(R.id.informasi)).perform(click());
        Espresso.onView(withId(R.id.tvInfoUsername)).check(myIsDisplayed());
        Espresso.onView(withId(R.id.tvInfoUsername)).perform(click());
        Espresso.onView(withId(R.id.view)).check(myIsDisplayed());
        Thread.sleep(3000);
        pressBack();

    }
}
