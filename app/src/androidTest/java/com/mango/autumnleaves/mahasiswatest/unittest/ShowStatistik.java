package com.mango.autumnleaves.mahasiswatest.unittest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.LoginActivity;
import com.mango.autumnleaves.ui.activity.mahasiswa.DashboardMahasiswaActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class ShowStatistik {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<DashboardMahasiswaActivity> activityTestRule = new ActivityTestRule<>(DashboardMahasiswaActivity.class);

    @Test
    public void showHistory() throws InterruptedException {

        Espresso.onView(withId(R.id.informasi)).perform(click());
        Espresso.onView(withId(R.id.linearStatistik)).check(myIsDisplayed());
        Espresso.onView(withId(R.id.linearStatistik)).perform(click());
        Espresso.onView(withId(R.id.rvStatistik)).check(myIsDisplayed());
        Thread.sleep(3000);
        pressBack();

    }
}
