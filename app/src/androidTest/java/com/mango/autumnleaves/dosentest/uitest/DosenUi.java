package com.mango.autumnleaves.dosentest.uitest;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.login.LoginActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class DosenUi {
    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule = new ActivityTestRule<>(LoginActivity.class);

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Test
    public void DosenUi() throws InterruptedException {
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

        Espresso.onView(withId(R.id.constraintKelas)).perform(click());
        Thread.sleep(5000);
        Espresso.onView(withId(R.id.btnSubmit)).check(myIsDisplayed());
        pressBack();

        Espresso.onView(withId(R.id.bap_menu)).perform(click());
        Espresso.onView(withId(R.id.rvBapDosen)).check(myIsDisplayed());
        Thread.sleep(2000);

        Espresso.onView(withId(R.id.jadwal_dosen)).perform(click());
        Espresso.onView(withId(R.id.rvJadwalDosen)).check(myIsDisplayed());
        Thread.sleep(1000);

        Espresso.onView(withId(R.id.account_menu)).perform(click());
        Espresso.onView(withId(R.id.tvUsernameDosen)).perform(click());
        Espresso.onView(withId(R.id.profileImgDosen)).check(myIsDisplayed());
        pressBack();

        Espresso.onView(withId(R.id.account_menu)).perform(click());
        Espresso.onView(withId(R.id.tvInfoLogoutDosen)).perform(click());
        Thread.sleep(1000);
        onView(withText("Keluar")).perform(click());

    }
}
