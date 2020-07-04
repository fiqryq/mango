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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.comparesEqualTo;

@RunWith(AndroidJUnit4.class)
public class ButtonPresensiTest {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void ButtonPresensiTest() throws InterruptedException {
        Thread.sleep(10000);
        //Button Presensi
        onData(anything()).inAdapterView(withId(R.id.gridView)).atPosition(0).
                onChildView(withId(R.id.linearLayout)).perform(click());
        Thread.sleep(3000);
        Espresso.onView(withId(R.id.btsPresensi)).check(myIsDisplayed());
        Thread.sleep(5000);
        Espresso.onView(withId(R.id.btsPresensi)).perform(click());
        Thread.sleep(5000);
        onView(withText("OK")).perform(click()).check(myIsDisplayed());
    }
}
