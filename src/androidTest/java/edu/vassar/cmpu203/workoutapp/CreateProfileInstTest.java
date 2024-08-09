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
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;

import edu.vassar.cmpu203.workoutapp.Controller.MainActivity;
import edu.vassar.cmpu203.workoutapp.Model.Profile;

@RunWith(AndroidJUnit4.class)
public class CreateProfileInstTest extends AddMiscThings {
    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * testing the values of the Create profile screen and checks to make sure that
     * we can input new information without crashing the app
     */
    @Test
    public void createProfileTest() {

        // click the signup button on the home screen
        ViewInteraction signUpButton = Espresso.onView(ViewMatchers.withId(R.id.signUpButton));
        signUpButton.perform(ViewActions.click());

        //type in a new username
        ViewInteraction usernameVi = Espresso.onView(ViewMatchers.withId(R.id.UsernameText));
        usernameVi.perform(ViewActions.replaceText("ymi456"));

        //types in password
        ViewInteraction passwordVi = Espresso.onView(ViewMatchers.withId(R.id.passwordEditText));
        passwordVi.perform((ViewActions.replaceText("abcdefg123!!")));

        // type new bio
        ViewInteraction bioVi = Espresso.onView(ViewMatchers.withId(R.id.bioEditText));
        bioVi.perform(ViewActions.replaceText("I love to workout"));

        //click the create button to make sure that we can get to the new screen
        ViewInteraction createButtonVi = Espresso.onView(ViewMatchers.withId(R.id.createButton));
        createButtonVi.perform(ViewActions.click());

        // allow for retrieval
        SystemClock.sleep(2000);

        Profile profile = new Profile();
        profile.setUsername("ymi456");

        // check that snackbar appears
        Matcher<View> snackbarMatcher0 = ViewMatchers.withText(R.string.CreationSuccessful);
        ViewInteraction snackBarVi0 = Espresso.onView(snackbarMatcher0);
        snackBarVi0.check(ViewAssertions.matches(snackbarMatcher0));

        this.persistenceFacade.removeUser(profile);

    }

    /**
     * Test to make sure that the snackbar will pop up
     * when all the needed data is not entered
     */
    @Test
    public void testSnackbar() {

        // click the signup button on the home screen
        ViewInteraction signUpButton = Espresso.onView(ViewMatchers.withId(R.id.signUpButton));
        signUpButton.perform(ViewActions.click());

        //Enter a blank username but has a password and a bio
        ViewInteraction usernameVi = Espresso.onView(ViewMatchers.withId(R.id.UsernameText));
        usernameVi.perform(ViewActions.replaceText(""));
        ViewInteraction passwordVi = Espresso.onView(ViewMatchers.withId(R.id.passwordEditText));
        passwordVi.perform(ViewActions.replaceText("password"));
        ViewInteraction bioVi = Espresso.onView(ViewMatchers.withId(R.id.bioEditText));
        bioVi.perform(ViewActions.replaceText("I love to workout"));
        ViewInteraction createButtonVi = Espresso.onView(ViewMatchers.withId(R.id.createButton));
        createButtonVi.perform(ViewActions.click());

        //the snackbar matcher
        Matcher<View> snackbarMatcher = ViewMatchers.withText("Username, password, and bio are mandatory!");
       ViewInteraction snackBarVi = Espresso.onView(snackbarMatcher);
       snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

       //sleep between each test so that the snackbar will go away
       SystemClock.sleep(5000);

       //Have entered a bio and username but no password
        bioVi.perform(ViewActions.replaceText("Bio"));
        passwordVi.perform((ViewActions.replaceText("")));
        usernameVi.perform(ViewActions.replaceText("Username"));
        createButtonVi.perform(ViewActions.click());

        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

        SystemClock.sleep(5000);

        //entered user name and password but no bio
        bioVi.perform(ViewActions.replaceText(""));
        passwordVi.perform(ViewActions.replaceText("password"));
        usernameVi.perform(ViewActions.replaceText("Username"));
        createButtonVi.perform(ViewActions.click());
        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

        SystemClock.sleep(5000);

        //did not enter any of the required data
        createButtonVi.perform(ViewActions.click());
        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

        SystemClock.sleep(5000);

        //entered just a password
        passwordVi.perform(ViewActions.replaceText("password"));
        createButtonVi.perform(ViewActions.click());
        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

        SystemClock.sleep(5000);

        //only entered a username
        usernameVi.perform(ViewActions.replaceText("Username"));
        createButtonVi.perform(ViewActions.click());
        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

        SystemClock.sleep(5000);

        //only entered a username
        bioVi.perform(ViewActions.replaceText("Bio"));
        createButtonVi.perform(ViewActions.click());
        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

        // create an account
        usernameVi.perform(ViewActions.replaceText("Arun"));
        bioVi.perform(ViewActions.replaceText("Bio"));
        passwordVi.perform(ViewActions.replaceText("password"));
        createButtonVi.perform(ViewActions.click());
        Profile profile = new Profile();
        profile.setUsername("Arun");

        SystemClock.sleep(5000);

        ViewInteraction profileButton = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        profileButton.perform(ViewActions.click());
        ViewInteraction logoutButton = Espresso.onView(ViewMatchers.withId(R.id.LogoutButton));
        logoutButton.perform(ViewActions.click());

        SystemClock.sleep(5000);

        signUpButton.perform(ViewActions.click());

        // enter the same username of an existing user
        usernameVi.perform(ViewActions.replaceText("Arun"));
        bioVi.perform(ViewActions.replaceText("Bio"));
        passwordVi.perform(ViewActions.replaceText("password"));
        createButtonVi.perform(ViewActions.click());

        Matcher<View> snackbarMatcher0 = ViewMatchers.withText(R.string.userExists);
        ViewInteraction snackBarVi0 = Espresso.onView(snackbarMatcher0);
        snackBarVi0.check(ViewAssertions.matches(snackbarMatcher0));

        this.persistenceFacade.removeUser(profile);


    }
}
