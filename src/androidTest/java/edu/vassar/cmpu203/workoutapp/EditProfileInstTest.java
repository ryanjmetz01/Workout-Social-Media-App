package edu.vassar.cmpu203.workoutapp;

import android.os.SystemClock;
import android.view.View;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matcher;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.vassar.cmpu203.workoutapp.Controller.MainActivity;
import edu.vassar.cmpu203.workoutapp.Model.Profile;

@RunWith(AndroidJUnit4.class)
public class EditProfileInstTest extends AddMiscThings{

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * a test that goes to the profile screen and attempts to edit
     * the current User's profile
     * Attempts are made to set bio and password to nothing
     * and the success of a change is tested
     */
    @Test
    public void editProfileTest(){
        //Step 1:
        // go to the edit profile screen
        Profile profile = createProfile("tester", "1", "workout is fun");

        SystemClock.sleep(5000);

        ViewInteraction profileButton = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        profileButton.perform(ViewActions.click());
        ViewInteraction editProfileButton = Espresso.onView(ViewMatchers.withId(R.id.editProfileButton));
        editProfileButton.perform(ViewActions.click());

        //Step 2:
        // attempt to enter nothing into the password
        ViewInteraction setPasswordButton = Espresso.onView(ViewMatchers.withId(R.id.setPasswordButton));
        setPasswordButton.perform(ViewActions.click());
        // show snackbar appears
        Matcher<View> snackbarMatcher0 = ViewMatchers.withText("Can't have an empty Password!");
        ViewInteraction snackBarVi0 = Espresso.onView(snackbarMatcher0);
        snackBarVi0.check(ViewAssertions.matches(snackbarMatcher0));

        SystemClock.sleep(5000);

        //Step 3:
        // attempt to enter nothing into bio
        ViewInteraction bioSetButton = Espresso.onView(ViewMatchers.withId(R.id.setBioButton));
        bioSetButton.perform(ViewActions.click());
        // show snackbar appears
        Matcher<View> snackbarMatcher1 = ViewMatchers.withText("Can't have an empty bio!");
        ViewInteraction snackBarVi1 = Espresso.onView(snackbarMatcher1);
        snackBarVi1.check(ViewAssertions.matches(snackbarMatcher1));

        SystemClock.sleep(5000);

        //Step 4:
        // change the bio
        ViewInteraction bioField = Espresso.onView(ViewMatchers.withId(R.id.EditBioText));
        bioField.perform(ViewActions.replaceText("new bio"));
        profile.setBio("new bio");
        bioSetButton.perform(ViewActions.click());
        // go back to profile and see that it has changed
        ViewInteraction backButton = Espresso.onView(ViewMatchers.withId(R.id.doneButton));
        backButton.perform(ViewActions.click());
        profileButton.perform(ViewActions.click());
        ViewInteraction profileBio = Espresso.onView(ViewMatchers.withId(R.id.ProfileViewBio));
        profileBio.check(ViewAssertions.matches(ViewMatchers.withText(profile.getBio())));

        //Step 5:
        // change the password
        editProfileButton.perform(ViewActions.click());
        ViewInteraction passwordField = Espresso.onView(ViewMatchers.withId(R.id.EditPasswordText));
        passwordField.perform(ViewActions.replaceText("newPassword"));
        setPasswordButton.perform(ViewActions.click());
        profile.setPasswordFromString("newPassword");

        // logout and then login with new password
        backButton.perform(ViewActions.click());
        profileButton.perform(ViewActions.click());
        ViewInteraction logoutButton = Espresso.onView(ViewMatchers.withId(R.id.LogoutButton));
        logoutButton.perform(ViewActions.click());

        logIn(profile.getUsername(), "newPassword");

        SystemClock.sleep(2000);

        Matcher<View> snackbarMatcher2 = ViewMatchers.withText(R.string.login);
        ViewInteraction snackBarVi2 = Espresso.onView(snackbarMatcher2);
        snackBarVi2.check(ViewAssertions.matches(snackbarMatcher2));

        this.persistenceFacade.removeUser(profile);
    }

}
