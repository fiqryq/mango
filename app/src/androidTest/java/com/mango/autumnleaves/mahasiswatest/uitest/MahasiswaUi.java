package com.mango.autumnleaves.mahasiswatest.uitest;

import android.app.Activity;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.LoginActivity;
import com.mango.autumnleaves.activity.mahasiswa.DashboardMahasiswaActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MahasiswaUi {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void MahasiswaUi() throws InterruptedException {
        // Login
        String username = "febbydahlan034@gmail.com";
        String password = "123456";

        Espresso.onView(ViewMatchers.withId(R.id.etusername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etpassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(5000);

        // Test Show Jadwal Activity
        Espresso.onView(ViewMatchers.withId(R.id.jadwal)).perform(click());
        Thread.sleep(5000);
        pressBack();

        // Test Show Activity Hostory
        Thread.sleep(5000);
        Espresso.onView(ViewMatchers.withId(R.id.history)).perform(click());
        pressBack();

        // Test
        Espresso.onView(ViewMatchers.withId(R.id.informasi)).perform(click());
        Espresso.onView(ViewMatchers.withId(R.id.tvInfoUsername)).perform(click());
        Thread.sleep(3000);
        Espresso.onView(ViewMatchers.withId(R.id.imv_back)).perform(click());

        // Logout
        onView(withId(R.id.tvLogoutMhs)).perform(click());
        Thread.sleep(3000);
        onView(withText("Logout")).perform(click()).check(myIsDisplayed());
    }
}
