package com.mango.autumnleaves.mahasiswatest.unittest;

import android.widget.GridView;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewAssertion;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import com.mango.autumnleaves.R;
import com.mango.autumnleaves.activity.mahasiswa.DashboardMahasiswaActivity;

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
import static java.util.EnumSet.allOf;
import static org.hamcrest.Matchers.anything;

@RunWith(AndroidJUnit4.class)
public class ButtonPresensiTest {

    private ViewAssertion myIsDisplayed() {
        return (view, noViewFoundException) -> isDisplayed();
    }

    @Rule
    public ActivityTestRule<DashboardMahasiswaActivity> activityTestRule = new ActivityTestRule<>(DashboardMahasiswaActivity.class);

    @Test
    public void ButtonPresensiTest() throws InterruptedException {

        // Login
        String username = "febbydahlan034@gmail.com";
        String password = "123456";

        Espresso.onView(ViewMatchers.withId(R.id.etusername)).perform(typeText(username));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.etpassword)).perform(typeText(password));
        Espresso.closeSoftKeyboard();
        Espresso.onView(withId(R.id.button_login)).perform(click());
        Thread.sleep(5000);

        //Button Presensi
        onData(anything()).inAdapterView(withId(R.id.gridView)).atPosition(0).
                onChildView(withId(R.id.linearLayout)).perform(click());
        Thread.sleep(3000);
        Espresso.onView(withId(R.id.btsPresensi)).perform(click());
        Thread.sleep(3000);
        onView(withText("ok")).perform(click()).check(myIsDisplayed());
        pressBack();

        onView(withId(R.id.informasi)).perform(click());
        onView(withId(R.id.tvLogoutMhs)).perform(click());
        Thread.sleep(3000);
        onView(withText("Logout")).perform(click()).check(myIsDisplayed());
    }
}
