package com.mango.autumnleaves.mahasiswatest.uitest;

import android.widget.TextView;

import static androidx.test.espresso.Espresso.onData;
import static org.hamcrest.core.StringContains.containsString;
import androidx.test.espresso.Espresso;
import static org.hamcrest.Matchers.anything;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.ui.activity.LoginActivity;
import com.mango.autumnleaves.ui.activity.mahasiswa.DashboardMahasiswaActivity;

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
public class MahasiswaUi {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<LoginActivity> activityTestRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Test
    public void MahasiswaUi() throws InterruptedException {
        // Login
        String username = "febbydahlan034@gmail.com";
        String password = "123456";

        Espresso.onView(ViewMatchers.withId(R.id.etusername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etusername)).check(matches(withText(username)));

        Espresso.onView(withId(R.id.etpassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etpassword)).check(matches(withText(password)));

        Espresso.onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(8000);
        onView(withId(R.id.mainMenu)).check(myIsDisplayed());

        // Test Show Jadwal Activity
        Espresso.onView(ViewMatchers.withId(R.id.jadwal)).perform(click());
        Thread.sleep(5000);
        onView(withId(R.id.tvJadwalHariIni)).check(matches(withText("Hari Ini")));
        pressBack();

        // Test Show Activity History
        Espresso.onView(ViewMatchers.withId(R.id.history)).perform(click());
        Thread.sleep(5000);
        pressBack();

        Espresso.onView(ViewMatchers.withId(R.id.informasi)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.tvInfoUsername)).check(myIsDisplayed());

        Thread.sleep(3000);
        Espresso.onView(withId(R.id.tvInfoUsername)).perform(click());
        Thread.sleep(3000);
        onView(withId(R.id.etusername)).check(myIsDisplayed());
        pressBack();

        Espresso.onView(withId(R.id.linearStatistik)).perform(click());
        Thread.sleep(5000);
        pressBack();

        // Logout
        onView(withId(R.id.tvLogoutMhs)).perform(click());
        Thread.sleep(5000);
        onView(withText("Keluar")).perform(click());
    }
}
