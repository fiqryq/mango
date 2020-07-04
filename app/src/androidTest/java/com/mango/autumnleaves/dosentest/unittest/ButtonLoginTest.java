package com.mango.autumnleaves.dosentest.unittest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class ButtonLoginTest {
    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ButtonLoginTest() throws InterruptedException {
        // Login
        String username = "hettihd@tass.telkomuniversity.ac.id";
        String password = "06750056";

        Espresso.onView(withId(R.id.etusername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etusername)).check(matches(withText(username)));

        Espresso.onView(withId(R.id.etpassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etpassword)).check(matches(withText(password)));

        Espresso.onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(5000);
        Espresso.onView(withId(R.id.textView5)).check(myIsDisplayed());
    }
}
