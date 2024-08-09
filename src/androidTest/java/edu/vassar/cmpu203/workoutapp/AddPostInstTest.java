package edu.vassar.cmpu203.workoutapp;

import android.os.SystemClock;
import android.view.View;
import android.widget.SeekBar;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
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
import edu.vassar.cmpu203.workoutapp.Model.Cardio;
import edu.vassar.cmpu203.workoutapp.Model.Post;
import edu.vassar.cmpu203.workoutapp.Model.Profile;
import edu.vassar.cmpu203.workoutapp.Model.Strength;
import edu.vassar.cmpu203.workoutapp.Model.Workout;
import edu.vassar.cmpu203.workoutapp.Persistence.FirestoreFacade;
import edu.vassar.cmpu203.workoutapp.Persistence.IPersistenceFacade;

@RunWith(AndroidJUnit4.class)
public class AddPostInstTest extends AddMiscThings {

    protected IPersistenceFacade persistenceFacade = new FirestoreFacade();

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Test to see if someone is able to post with a Cardio Workout
     * while creating a post to compare the results of the app too
     */
    @Test
    public void addCardioPostTest() {
        //create profile screen -> leaves default values and clicks create
        //creates a new Profile and sets the Username to default
        Profile profile = createProfile("Username", "Password", "Bio");

        SystemClock.sleep(5000);

        ViewInteraction addPostButtonVi = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostButtonVi.perform(ViewActions.click());
        Post post = new Post(profile);
        Workout workout = new Workout();

        //create post screen, checks that default caption text is there
        ViewInteraction postCaptionVi = Espresso.onView(ViewMatchers.withId(R.id.postCaption));

        // checks that the default workout text is there
        ViewInteraction postWorkoutVi = Espresso.onView(ViewMatchers.withId(R.id.postWorkout));
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText("No workout added")));

        //create the values for the workout to be passed
        boolean[] values = new boolean[3];
        values[0] = true;

        //create and add the workout
        workout = addWorkoutTests(1, workout,values,"50",3,"A hard workout", "None");
        post.setWorkout(workout);

        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText(workout.toString())));

        //create post screen, check to see of default text for caption is there
        //enter new caption
        ViewInteraction postCapEdVi = Espresso.onView(ViewMatchers.withId(R.id.captionTextBox));
        postCapEdVi.perform(ViewActions.replaceText("A fun workout"));

        //click the caption button and checks to see that the caption text has changed
        ViewInteraction captionButtonVi = Espresso.onView(ViewMatchers.withId(R.id.captionButton));
        captionButtonVi.perform(ViewActions.click());
        postCaptionVi.check(ViewAssertions.matches(ViewMatchers.withText("A fun workout")));
        post.addCaption("A fun workout");

        // clicks on the post button
        ViewInteraction postButtonVi = Espresso.onView(ViewMatchers.withId(R.id.postButton));
        postButtonVi.perform(ViewActions.click());

        //checks to see if the post1 text in the feed is the same as the post that was just created
        ViewInteraction post1Vi = Espresso.onView(ViewMatchers.withSubstring("A fun workout"));
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));

        //Check if number of posts is updated
        ViewInteraction ProfileButtonVi = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        ProfileButtonVi.perform(ViewActions.click());
        ViewInteraction postsNumberVi = Espresso.onView(ViewMatchers.withId(R.id.PostNumberDisplay));
        postsNumberVi.check(ViewAssertions.matches(ViewMatchers.withText("1")));


        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
        SystemClock.sleep(5000);

    }

    /**
     * Creating a post that contains a strength workout
     */
    @Test
    public void addStrengthPostTest() {
        //create profile screen -> leaves default values and clicks create
        //creates a new Profile and sets the Username to default
        SystemClock.sleep(10000);
        Profile profile = createProfile("Username", "Password", "Bio");

        SystemClock.sleep(5000);

        //Feed screen, checks that Post1 has the default text, clicks on the add post button
        ViewInteraction addPostButtonVi = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostButtonVi.perform(ViewActions.click());
        Post post = new Post(profile);
        Workout workout = new Workout();

        //create post screen, checks that default caption text is there
        ViewInteraction postCaptionVi = Espresso.onView(ViewMatchers.withId(R.id.postCaption));
        // checks that the default workout text is there
        ViewInteraction postWorkoutVi = Espresso.onView(ViewMatchers.withId(R.id.postWorkout));
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText("No workout added")));

        //create the values for the workout
        boolean[] values = new boolean[4];
        values[0] = true;
        values[2] = true;

        //create and add the workout
        workout = addWorkoutTests(2, workout,values,"20",4,"pushups and what not", "None");
        post.setWorkout(workout);

        //make sure the workout matches the
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText(workout.toString())));

        //create post screen, check to see of default text for caption is there
        //enter new caption
        ViewInteraction postCapEdVi = Espresso.onView(ViewMatchers.withId(R.id.captionTextBox));
        postCapEdVi.perform(ViewActions.replaceText("A funny workout"));

        //click the caption button and checks to see that the caption text has changed
        ViewInteraction captionButtonVi = Espresso.onView(ViewMatchers.withId(R.id.captionButton));
        captionButtonVi.perform(ViewActions.click());
        postCaptionVi.check(ViewAssertions.matches(ViewMatchers.withText("A funny workout")));
        post.addCaption("A funny workout");

        // clicks on the post button
        ViewInteraction postButtonVi = Espresso.onView(ViewMatchers.withId(R.id.postButton));
        postButtonVi.perform(ViewActions.click());

        //checks to see if the post1 text in the feed is the same as the post that was just created
        ViewInteraction post1Vi = Espresso.onView(ViewMatchers.withSubstring("A funny workout"));
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));

        //Check if number of posts is updated
        ViewInteraction ProfileButtonVi = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        ProfileButtonVi.perform(ViewActions.click());
        ViewInteraction postsNumberVi = Espresso.onView(ViewMatchers.withId(R.id.PostNumberDisplay));
        postsNumberVi.check(ViewAssertions.matches(ViewMatchers.withText("1")));
        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
        SystemClock.sleep(5000);
    }

    /**
     * Creating a post that contains a Mobility workout
     */
    @Test
    public void addMobilityPostTest() {
        //create profile screen -> leaves default values and clicks create
        //creates a new Profile and sets the Username to default
        SystemClock.sleep(10000);
        Profile profile = createProfile("Username", "Password", "Bio");
        SystemClock.sleep(5000);

        ViewInteraction addPostButtonVi = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostButtonVi.perform(ViewActions.click());
        Post post = new Post(profile);
        Workout workout = new Workout();

        //create post screen, checks that default caption text is there
        ViewInteraction postCaptionVi = Espresso.onView(ViewMatchers.withId(R.id.postCaption));

        // checks that the default workout text is there
        ViewInteraction postWorkoutVi = Espresso.onView(ViewMatchers.withId(R.id.postWorkout));
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText("No workout added")));

        //create the values for the workout
        boolean[] values = new boolean[3];
        values[0] = true;
        values[2] = true;

        //create and add the workout
        workout = addWorkoutTests(3, workout, values,"20",4,"stretching and what not", "None");
        post.setWorkout(workout);

        //make sure the workout matches the
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText(workout.toString())));

        //create post screen, check to see of default text for caption is there
        //enter new caption
        ViewInteraction postCapEdVi = Espresso.onView(ViewMatchers.withId(R.id.captionTextBox));
        postCapEdVi.perform(ViewActions.replaceText("A fundamental workout"));

        //click the caption button and checks to see that the caption text has changed
        ViewInteraction captionButtonVi = Espresso.onView(ViewMatchers.withId(R.id.captionButton));
        captionButtonVi.perform(ViewActions.click());
        postCaptionVi.check(ViewAssertions.matches(ViewMatchers.withText("A fundamental workout")));
        post.addCaption("A fundamental workout");

        // clicks on the post button
        ViewInteraction postButtonVi = Espresso.onView(ViewMatchers.withId(R.id.postButton));
        postButtonVi.perform(ViewActions.click());

        //checks to see if the post1 text in the feed is the same as the post that was just created
        ViewInteraction post1Vi = Espresso.onView(ViewMatchers.withSubstring("A fundamental workout"));
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));

        //Check if number of posts is updated
        ViewInteraction ProfileButtonVi = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        ProfileButtonVi.perform(ViewActions.click());
        ViewInteraction postsNumberVi = Espresso.onView(ViewMatchers.withId(R.id.PostNumberDisplay));
        postsNumberVi.check(ViewAssertions.matches(ViewMatchers.withText("1")));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

    /**
     * Adding multiple posts to the feed to test that we can view multiple posts at once
     * --Create posts using much of the same logic as above
     */
    @Test
    public void addMultiplePostsTest() {
        //create profile screen -> leaves default values and clicks create
        //creates a new Profile and sets the Username to default
        SystemClock.sleep(10000);
        Profile profile = createProfile("Username", "Password", "Bio");
        SystemClock.sleep(5000);
        //START OF FIRST POST

        ViewInteraction addPostButtonVi = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostButtonVi.perform(ViewActions.click());
        Post post = new Post(profile);
        Workout workout = new Workout();

        //create post screen, checks that default caption text is there
        ViewInteraction postCaptionVi = Espresso.onView(ViewMatchers.withId(R.id.postCaption));

        // checks that the default workout text is there
        ViewInteraction postWorkoutVi = Espresso.onView(ViewMatchers.withId(R.id.postWorkout));
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText("No workout added")));

        //create the values for the new workout
        boolean[] values = new boolean[3];
        values[1] = true;
        values[2] = true;

        //add the workout ot the post
        workout = addWorkoutTests(1, workout,values, "100", 1, "Five sprints", "None");
        post.setWorkout(workout);

        //check that the workout is displaying correctly in the create post screen
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText(workout.toString())));

        //create post screen, check to see of default text for caption is there
        //enter new caption
        ViewInteraction postCapEdVi = Espresso.onView(ViewMatchers.withId(R.id.captionTextBox));
        postCapEdVi.perform(ViewActions.replaceText("Did some sprints today"));

        //click the caption button and checks to see that the caption text has changed
        ViewInteraction captionButtonVi = Espresso.onView(ViewMatchers.withId(R.id.captionButton));
        captionButtonVi.perform(ViewActions.click());
        postCaptionVi.check(ViewAssertions.matches(ViewMatchers.withText("Did some sprints today")));
        post.addCaption("Did some sprints today");

        // clicks on the post button
        ViewInteraction postButtonVi = Espresso.onView(ViewMatchers.withId(R.id.postButton));
        postButtonVi.perform(ViewActions.click());

        //checks to see if the post1 text in the feed is the same as the post that was just created
        ViewInteraction post1Vi = Espresso.onView(ViewMatchers.withSubstring("Did some sprints today"));
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));


        // START OF SECOND POST

        addPostButtonVi.perform(ViewActions.click());
        Post post1 = new Post(profile);
        Workout workout1;

        //create the values for the new workout
        boolean[] values1 = new boolean[4];
        values1[0] = true;
        values1[2] = true;
        workout1 = new Strength(values1);

        //add the new workout to the post
        workout1 = addWorkoutTests(2, workout1, values1, "40", 2, "Lots and lots of push-ups", "None");
        post1.setWorkout(workout1);

        //check that the workout is displayed in the add post screen as intended
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText(workout1.toString())));

        //create post screen, check to see of default text for caption is there
        //enter new caption
        postCapEdVi.perform(ViewActions.replaceText("My arms are sore"));

        //click the caption button and checks to see that the caption text has changed
        captionButtonVi.perform(ViewActions.click());
        postCaptionVi.check(ViewAssertions.matches(ViewMatchers.withText("My arms are sore")));
        post1.addCaption("My arms are sore");

        // clicks on the post button
        postButtonVi.perform(ViewActions.click());

        //checks that the posts are displayed like they are supposed to
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        ViewInteraction post2Vi = Espresso.onView(ViewMatchers.withSubstring("My arms are sore"));
        post2Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));

        // START OF THIRD POST

        addPostButtonVi.perform(ViewActions.click());
        Post post2 = new Post(profile);
        Workout workout2;

        //create values for the workout
        boolean[] values2 = new boolean[4];
        values2[1] = true;
        values2[3] = true;
        workout2 = new Strength(values2);

        //add the workout
        workout2 = addWorkoutTests(2, workout2, values2, "30", 4, "Some good weights and what not", "None");
        post2.setWorkout(workout2);

        //check that the workout is displaying in the add post screen as intended
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText(workout2.toString())));

        //create post screen, check to see of default text for caption is there
        //enter new caption
        postCapEdVi.perform(ViewActions.replaceText("My legs are sore"));

        //click the caption button and checks to see that the caption text has changed
        captionButtonVi.perform(ViewActions.click());
        postCaptionVi.check(ViewAssertions.matches(ViewMatchers.withText("My legs are sore")));
        post2.addCaption("My legs are sore");

        // clicks on the post button
        postButtonVi.perform(ViewActions.click());

        //checks that all of the posts are displaying like they are supposed to
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        post2Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));
        ViewInteraction post3Vi = Espresso.onView(ViewMatchers.withSubstring("My legs are sore"));
        post3Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));
        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

    /**
     * Makes sure that someone can post only text post with no workout
     */
    @Test
    public void addPostNoWorkoutTest() {
        //navigate the create profile screen
        SystemClock.sleep(10000);
        Profile profile = createProfile("Username", "Password", "Bio");
        SystemClock.sleep(5000);
        ViewInteraction addPostButtonVi = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostButtonVi.perform(ViewActions.click());
        Post post = new Post(profile);

        //enter a new caption and click the add caption button
        ViewInteraction postCapEdVi = Espresso.onView(ViewMatchers.withId(R.id.captionTextBox));
        postCapEdVi.perform(ViewActions.replaceText("This is pretty chill"));
        ViewInteraction captionButtonVi = Espresso.onView(ViewMatchers.withId(R.id.captionButton));
        captionButtonVi.perform(ViewActions.click());
        post.addCaption("This is pretty chill");

        // click on the post button
        ViewInteraction postButtonVi = Espresso.onView(ViewMatchers.withId(R.id.postButton));
        postButtonVi.perform(ViewActions.click());

        //check to make sure that the post is displaying like it is supposed to
        //checks to see if the post1 text in the feed is the same as the post that was just created
        ViewInteraction post1Vi = Espresso.onView(ViewMatchers.withSubstring("This is pretty chill"));
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));

        //Check if number of posts is updated
        ViewInteraction ProfileButtonVi = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        ProfileButtonVi.perform(ViewActions.click());
        ViewInteraction postsNumberVi = Espresso.onView(ViewMatchers.withId(R.id.PostNumberDisplay));
        postsNumberVi.check(ViewAssertions.matches(ViewMatchers.withText("1")));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

    /**
     * This is a test to make sure that someone can post a workout
     * with no caption
     * -- follows much of the same logic as above tests
     */
    @Test
    public void addPostNoCaption() {
        //create profile screen -> leaves default values and clicks create
        //creates a new Profile and sets the Username to default
        SystemClock.sleep(10000);
        Profile profile = createProfile("Username", "Password", "Bio");
        SystemClock.sleep(5000);

        ViewInteraction addPostButtonVi = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostButtonVi.perform(ViewActions.click());
        Post post = new Post(profile);
        Workout workout = new Workout();

        //create post screen, checks that default caption text is there
        ViewInteraction postCaptionVi = Espresso.onView(ViewMatchers.withId(R.id.postCaption));
        // checks that the default workout text is there
        ViewInteraction postWorkoutVi = Espresso.onView(ViewMatchers.withId(R.id.postWorkout));
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText("No workout added")));

        //create the values for the workout to be passed
        boolean[] values = new boolean[3];
        values[1] = true;

        //create and add the workout
        workout = addWorkoutTests(1, workout,values,"50",3,"Im running sprints", "None");
        post.setWorkout(workout);


        // clicks on the post button
        ViewInteraction postButtonVi = Espresso.onView(ViewMatchers.withId(R.id.postButton));
        postButtonVi.perform(ViewActions.click());

        //checks to see if the post1 text in the feed is the same as the post that was just created
        ViewInteraction post1Vi = Espresso.onView(ViewMatchers.withSubstring("Im running sprints"));
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));

        //Check if number of posts is updated
        ViewInteraction ProfileButtonVi = Espresso.onView(ViewMatchers.withId(R.id.ViewProfileButton));
        ProfileButtonVi.perform(ViewActions.click());
        ViewInteraction postsNumberVi = Espresso.onView(ViewMatchers.withId(R.id.PostNumberDisplay));
        postsNumberVi.check(ViewAssertions.matches(ViewMatchers.withText("1")));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

    /**
     * Testing that the snackbars will appear when they are supposed to when
     * incorrect values are entered
     */
    @Test
    public void testSnackBars(){
        //create profile screen -> leaves default values and clicks create
        //creates a new Profile and sets the Username to default
        SystemClock.sleep(10000);
        Profile profile = createProfile("Username", "Password", "Bio");
        SystemClock.sleep(5000);
        //click on the add post button on the feed screen
        ViewInteraction addPostButtonVi = Espresso.onView(ViewMatchers.withId(R.id.addButton));
        addPostButtonVi.perform(ViewActions.click());

        //click on the add workout button on the create post screen
        ViewInteraction addWorkoutButtonVi = Espresso.onView((ViewMatchers.withId(R.id.addWorkoutButton)));
        addWorkoutButtonVi.perform(ViewActions.click());

        //TEST 1: Seeing if snackbar appears if no workout type is chosen

        //set the length of the workout
        ViewInteraction lengthVi = Espresso.onView(ViewMatchers.withId(R.id.WorkoutLengthInput));
        lengthVi.perform(ViewActions.replaceText("20"));

        //set the difficulty of the workout
        ViewInteraction difficultyVi = Espresso.onView(ViewMatchers.withId(R.id.WorkoutDifficultyInput));
        difficultyVi.perform(ViewActions.swipeRight());

        //set the description for the workout
        ViewInteraction workoutDescriptionVi = Espresso.onView(ViewMatchers.withId(R.id.WorkoutDescriptionInput));
        workoutDescriptionVi.perform(ViewActions.replaceText("Description"));

        //click the create workout button
        ViewInteraction createWorkoutButtonVi = Espresso.onView(ViewMatchers.withId(R.id.AddWorkoutButton));
        createWorkoutButtonVi.perform(ViewActions.click());

        //check that the snackbar appears
        Matcher<View> snackbarMatcher1 = ViewMatchers.withText("Choosing a workout type is mandatory!");
        ViewInteraction snackBarVi1 = Espresso.onView(snackbarMatcher1);
        snackBarVi1.check(ViewAssertions.matches(snackbarMatcher1));

        //sleep between each test so that the snackbar will go away
        SystemClock.sleep(3500);

        //TEST 2: Make sure a snackbar appears when a number is not entered for length

        //click on cardio button and add some attributes
        ViewInteraction cardioButtonVi = Espresso.onView(ViewMatchers.withId(R.id.CardioButton));
        cardioButtonVi.perform(ViewActions.click());
        ViewInteraction enduranceButtonVi = Espresso.onView(ViewMatchers.withId(R.id.EnduranceOption));
        enduranceButtonVi.perform(ViewActions.click());
        ViewInteraction setVi = Espresso.onView(ViewMatchers.withId(R.id.CardioSetButton));
        setVi.perform(ViewActions.click());

        //enter a string for the length
        lengthVi.perform(ViewActions.replaceText("adadfkad"));

        //enter a description
        workoutDescriptionVi.perform(ViewActions.replaceText("Description"));

        //set difficulty
        difficultyVi.perform(ViewActions.swipeRight());

        //click add workout
        createWorkoutButtonVi.perform(ViewActions.click());

        //test that the snackbar has appeared
        Matcher<View> snackbarMatcher2 = ViewMatchers.withText("Please enter a number for length");
        ViewInteraction snackBarVi2 = Espresso.onView(snackbarMatcher2);
        snackBarVi2.check(ViewAssertions.matches(snackbarMatcher2));

        SystemClock.sleep(3500);

        //TEST 3: Checking that snackbar will appear if length, description or difficulty is not present

        //NO DIFFICULTY

        //set length to a number
        lengthVi.perform(ViewActions.replaceText("5"));

        //unset difficulty
        difficultyVi.perform(ViewActions.swipeLeft());

        //set description
        workoutDescriptionVi.perform(ViewActions.replaceText("Description"));

        //click add workout
        createWorkoutButtonVi.perform(ViewActions.click());

        //check for snackbar
        Matcher<View> snackbarMatcher3 = ViewMatchers.withText("Length, difficulty and description are mandatory!");
        ViewInteraction snackBarVi3 = Espresso.onView(snackbarMatcher3);
        snackBarVi3.check(ViewAssertions.matches(snackbarMatcher3));

        SystemClock.sleep(3500);

        //NO LENGTH

        //set length to a nothing
        lengthVi.perform(ViewActions.replaceText(""));

        //set difficulty
        difficultyVi.perform(ViewActions.swipeRight());

        //set description
        workoutDescriptionVi.perform(ViewActions.replaceText("Description"));

        //click add workout
        createWorkoutButtonVi.perform(ViewActions.click());

        //check for snackbar
        snackBarVi3.check(ViewAssertions.matches(snackbarMatcher3));

        SystemClock.sleep(3500);

        //NO DESCRIPTION

        //set the length
        lengthVi.perform(ViewActions.replaceText("5"));

        //difficulty stays set

        //set description
        workoutDescriptionVi.perform(ViewActions.replaceText(""));

        //click add workout
        createWorkoutButtonVi.perform(ViewActions.click());

        //check for snackbar
        snackBarVi3.check(ViewAssertions.matches(snackbarMatcher3));

        SystemClock.sleep(3500);

        //ONLY LENGTH

        //set the length
        lengthVi.perform(ViewActions.replaceText("5"));

        //unset difficulty
        difficultyVi.perform(ViewActions.swipeLeft());

        //set description
        workoutDescriptionVi.perform(ViewActions.replaceText(""));

        //click add workout
        createWorkoutButtonVi.perform(ViewActions.click());

        //check for snackbar
        snackBarVi3.check(ViewAssertions.matches(snackbarMatcher3));

        SystemClock.sleep(3500);

        //ONLY DIFFICULTY

        //set length to a nothing
        lengthVi.perform(ViewActions.replaceText(""));

        //set difficulty
        difficultyVi.perform(ViewActions.swipeRight());

        //set description
        workoutDescriptionVi.perform(ViewActions.replaceText(""));

        //click add workout
        createWorkoutButtonVi.perform(ViewActions.click());

        //check for snackbar
        snackBarVi3.check(ViewAssertions.matches(snackbarMatcher3));

        SystemClock.sleep(3500);

        //ONLY DESCRIPTION

        //set length to a nothing
        lengthVi.perform(ViewActions.replaceText(""));

        //unset difficulty
        difficultyVi.perform(ViewActions.swipeLeft());

        //set description
        workoutDescriptionVi.perform(ViewActions.replaceText("Description"));

        //click add workout
        createWorkoutButtonVi.perform(ViewActions.click());

        //check for snackbar
        snackBarVi3.check(ViewAssertions.matches(snackbarMatcher3));

        SystemClock.sleep(3500);


        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

}
