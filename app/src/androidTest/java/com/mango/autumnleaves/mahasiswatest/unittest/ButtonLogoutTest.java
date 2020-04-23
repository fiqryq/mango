package com.mango.autumnleaves.mahasiswatest.unittest;

import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.mahasiswa.DashboardMahasiswaActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class ButtonLogoutTest {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<DashboardMahasiswaActivity> activityTestRule = new ActivityTestRule<>(DashboardMahasiswaActivity.class);

    @Test
    public void ButtonLogoutTest() throws InterruptedException {
        onView(withId(R.id.informasi)).perform(click());
        onView(withId(R.id.tvLogoutMhs)).perform(click());
        Thread.sleep(3000);
        onView(withText("Logout")).perform(click()).check(myIsDisplayed());
    }
}
