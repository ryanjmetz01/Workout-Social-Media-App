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
import edu.vassar.cmpu203.workoutapp.Model.Post;
import edu.vassar.cmpu203.workoutapp.Model.Profile;
import edu.vassar.cmpu203.workoutapp.Model.Workout;


@RunWith(AndroidJUnit4.class)
public class FollowInstTest extends AddMiscThings {

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * a test that creates two profiles and has one follow the other
     * makes sure that a follow request can be both declined and accepted
     */
    @Test
    public void followingTest(){
        //step 1:
        // create an account (1), log out to go back to home screen
        // create another account (2), add a post, and log out

        Profile profile = createProfile("tester1", "1", "hi");
        SystemClock.sleep(5000);


        ViewInteraction profileButton = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        profileButton.perform(ViewActions.click());

        ViewInteraction logoutButton = Espresso.onView(ViewMatchers.withId(R.id.LogoutButton));
        logoutButton.perform(ViewActions.click());

        Profile profile2 = createProfile("tester2", "1", "hi");
        SystemClock.sleep(5000);


        ViewInteraction addPostbutton = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostbutton.perform(ViewActions.click());
        Post post = new Post();
        Workout workout = new Workout();
        boolean[] values = new boolean[3];
        values[1] = true;

        workout = addWorkoutTests(1, workout, values, "20", 4, "A run", "None");
        post.setWorkout(workout);



        ViewInteraction postCapEdVi = Espresso.onView(ViewMatchers.withId(R.id.captionTextBox));
        postCapEdVi.perform(ViewActions.replaceText("A fun workout"));
        ViewInteraction captionButtonVi = Espresso.onView(ViewMatchers.withId(R.id.captionButton));
        captionButtonVi.perform(ViewActions.click());

        ViewInteraction postButtonVi = Espresso.onView(ViewMatchers.withId(R.id.postButton));
        postButtonVi.perform(ViewActions.click());

        profileButton.perform(ViewActions.click());
        logoutButton.perform(ViewActions.click());
        SystemClock.sleep(5000);

        //Step 2:
        // log back in as first account (1), click on post made by other user to go to that person's(2) profile
        // check to make sure the information matches that person (2)
        logIn("tester1", "1");
        SystemClock.sleep(5000);

        ViewInteraction profileClick = Espresso.onView(ViewMatchers.withText("tester2"));
        profileClick.perform(ViewActions.click());

        ViewInteraction postNumberDisplay = Espresso.onView(ViewMatchers.withId(R.id.PostNumberDisplay));
        postNumberDisplay.check(ViewAssertions.matches(ViewMatchers.withText("1")));
        ViewInteraction followersDisplay = Espresso.onView(ViewMatchers.withId(R.id.FollowerDisplay));
        followersDisplay.check(ViewAssertions.matches(ViewMatchers.withText("0")));
        ViewInteraction followingDisplay = Espresso.onView(ViewMatchers.withId(R.id.FollowingDisplay));
        followingDisplay.check(ViewAssertions.matches(ViewMatchers.withText("0")));

        ViewInteraction profileUser = Espresso.onView(ViewMatchers.withId(R.id.OtherProfileUsername));
        profileUser.check(ViewAssertions.matches(ViewMatchers.withText(profile2.getUsername())));
        ViewInteraction profileBio = Espresso.onView(ViewMatchers.withId(R.id.OtherProfileViewBio));
        profileBio.check(ViewAssertions.matches(ViewMatchers.withText(profile.getBio())));

        //Step 3:
        // click on request follow, check to make sure the button is not enabled
        // go back to feed and then click on profile again
        // click on request follow again
        // see that snackbar appear
        // log out of this account (1)

        ViewInteraction requestFollow = Espresso.onView(ViewMatchers.withId(R.id.requestFollowButton));
        requestFollow.perform(ViewActions.click());
        requestFollow.check(ViewAssertions.matches(ViewMatchers.isNotEnabled()));

        ViewInteraction otherProfileBackButton = Espresso.onView(ViewMatchers.withId(R.id.backButton));
        otherProfileBackButton.perform(ViewActions.click());

        profileClick.perform(ViewActions.click());
        requestFollow.perform(ViewActions.click());

        Matcher<View> snackbarMatcher0 = ViewMatchers.withText("You already requested this person");
        ViewInteraction snackBarVi0 = Espresso.onView(snackbarMatcher0);
        snackBarVi0.check(ViewAssertions.matches(snackbarMatcher0));

        SystemClock.sleep(3500);

        otherProfileBackButton.perform(ViewActions.click());

        profileButton.perform(ViewActions.click());
        logoutButton.perform(ViewActions.click());
        SystemClock.sleep(5000);

        //Step 4:
        // log in to account (2) click on view profile
        // click on view follow requests
        // ensure that the follow request is present

        logIn("tester2", "1");
        SystemClock.sleep(5000);

        profileButton.perform(ViewActions.click());
        ViewInteraction viewRequests = Espresso.onView(ViewMatchers.withId(R.id.button5));
        viewRequests.perform(ViewActions.click());

        /*ViewInteraction requestText = Espresso.onView(ViewMatchers.withText("tester1"));
        requestText.check(ViewAssertions.matches(ViewMatchers.withText()));*/
        ViewInteraction acceptButton = Espresso.onView(ViewMatchers.withText("Accept"));
        acceptButton.check(ViewAssertions.matches(ViewMatchers.withText("Accept")));
        ViewInteraction declineButton = Espresso.onView(ViewMatchers.withText("Decline"));
        declineButton.check(ViewAssertions.matches(ViewMatchers.withText("Decline")));


        //Step 5:
        // click on decline
        // check that snackbar appears

        declineButton.perform(ViewActions.click());
        Matcher<View> snackbarMatcher1 = ViewMatchers.withText(R.string.decline);
        ViewInteraction snackBarVi1 = Espresso.onView(snackbarMatcher1);
        snackBarVi1.check(ViewAssertions.matches(snackbarMatcher1));

        SystemClock.sleep(3500);


        //Step 6:
        // View profile(2), make sure all values are the same
        // log out

        ViewInteraction backtoProfile = Espresso.onView(ViewMatchers.withId(R.id.backToProfileButton));
        backtoProfile.perform(ViewActions.click());

        ViewInteraction userFollowers = Espresso.onView(ViewMatchers.withId(R.id.FollowerDisplay));
        userFollowers.check(ViewAssertions.matches(ViewMatchers.withText("0")));

        logoutButton.perform(ViewActions.click());
        SystemClock.sleep(5000);


        //Step 7:
        // log back into first account (1)
        // click on account (2) profile, click on request follow
        // log out

        logIn("tester1", "1");
        SystemClock.sleep(5000);

        profileClick.perform(ViewActions.click());
        requestFollow.perform(ViewActions.click());
        otherProfileBackButton.perform(ViewActions.click());
        profileButton.perform(ViewActions.click());
        logoutButton.perform(ViewActions.click());
        SystemClock.sleep(5000);


        //Step 8:
        // log in to account (2)
        // go to view follow requests
        // see that the request is there
        // click on accept, make sure snackbar appears

        logIn("tester2", "1");
        SystemClock.sleep(5000);

        profileButton.perform(ViewActions.click());
        viewRequests.perform(ViewActions.click());

        //requestText.check(ViewAssertions.matches(ViewMatchers.withText(profile.getUsername() + " wants to follow you!")));
        acceptButton.check(ViewAssertions.matches(ViewMatchers.withText("Accept")));
        declineButton.check(ViewAssertions.matches(ViewMatchers.withText("Decline")));

        acceptButton.perform(ViewActions.click());

        Matcher<View> snackbarMatcher2 = ViewMatchers.withText(R.string.accept);
        ViewInteraction snackBarVi2 = Espresso.onView(snackbarMatcher2);
        snackBarVi2.check(ViewAssertions.matches(snackbarMatcher2));

        SystemClock.sleep(3500);


        // Step 9:
        // go to view profile, see that follower number has increased
        // log out

        backtoProfile.perform(ViewActions.click());
        userFollowers.check(ViewAssertions.matches(ViewMatchers.withText("1")));
        logoutButton.perform(ViewActions.click());
        SystemClock.sleep(5000);


        //Step 10:
        // log in to account (1)
        // check proflie to see that number of following has increased

        logIn("tester1", "1");
        SystemClock.sleep(5000);

        profileButton.perform(ViewActions.click());
        ViewInteraction userFollowing = Espresso.onView(ViewMatchers.withId(R.id.FollowingDisplay));
        userFollowing.check(ViewAssertions.matches(ViewMatchers.withText("1")));


        //Step 11:
        // go to feed click on account(2) post
        // click on request follow
        // check that snackbar appears saying you already follow

        ViewInteraction backToFeed = Espresso.onView(ViewMatchers.withId(R.id.button3));
        backToFeed.perform(ViewActions.click());
        profileClick.perform(ViewActions.click());

        requestFollow.perform(ViewActions.click());
        Matcher<View> snackbarMatcher3 = ViewMatchers.withText("You already follow this profile");
        ViewInteraction snackBarVi3 = Espresso.onView(snackbarMatcher3);
        snackBarVi3.check(ViewAssertions.matches(snackbarMatcher3));

        SystemClock.sleep(3500);

        //DONE!!!!


        this.persistenceFacade.removeUser(profile);
        this.persistenceFacade.removeUser(profile2);
        this.persistenceFacade.removePosts();

    }

}
