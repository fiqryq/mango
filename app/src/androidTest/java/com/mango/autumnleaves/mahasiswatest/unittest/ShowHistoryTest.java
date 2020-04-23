package com.mango.autumnleaves.mahasiswatest.unittest;

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

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class ShowHistoryTest {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void showHistory() throws InterruptedException {
        // Login
        String username = "febbydahlan034@gmail.com";
        String password = "123456";

        Espresso.onView(ViewMatchers.withId(R.id.etusername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etpassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.button_login)).perform(click());

        // Test Show Activity Hostory
        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(R.id.history)).perform(click());
        Thread.sleep(5000);
        onData(anything()).inAdapterView(withId(R.id.rvHistory)).atPosition(0).
                onChildView(withId(R.id.linearLayout)).perform(click());
        onView(withText("OK")).perform(click()).check(myIsDisplayed());
        pressBack();

        // Logout
        onView(withId(R.id.informasi)).perform(click());
        onView(withId(R.id.tvLogoutMhs)).perform(click());
        Thread.sleep(3000);
        onView(withText("Logout")).perform(click()).check(myIsDisplayed());
    }
}
