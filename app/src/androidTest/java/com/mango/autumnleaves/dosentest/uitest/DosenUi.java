package com.mango.autumnleaves.dosentest.uitest;

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

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.pressBack;
import static androidx.test.espresso.action.ViewActions.click;

import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.*;

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
        String username = "gbs@telkomuniversity.ac.id";
        String password = "13850009";

        Espresso.onView(ViewMatchers.withId(R.id.etusername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etpassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(5000);

        Espresso.onView(withId(R.id.constraintKelas)).perform(click());
        pressBack();

        Espresso.onView(withId(R.id.bap_menu)).perform(click());
        Espresso.onView(withId(R.id.account_menu)).perform(click());
        Espresso.onView(withId(R.id.tvUsernameDosen)).perform(click());
        Thread.sleep(1000);
        Espresso.onView(withId(R.id.imv_backDosen)).perform(click());
        Espresso.onView(withId(R.id.account_menu)).perform(click());
        Espresso.onView(withId(R.id.tvInfoLogoutDosen)).perform(click());
        Thread.sleep(1000);
        onView(withText("Logout")).perform(click()).check(myIsDisplayed());

    }
}
