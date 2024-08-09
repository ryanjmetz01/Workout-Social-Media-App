package edu.vassar.cmpu203.workoutapp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withSubstring;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import static org.hamcrest.Matchers.anything;


import android.os.SystemClock;
import android.view.View;

import org.hamcrest.Matcher;
import org.junit.Test;

import edu.vassar.cmpu203.workoutapp.Controller.MainActivity;
import edu.vassar.cmpu203.workoutapp.Model.Feed;
import edu.vassar.cmpu203.workoutapp.Model.Post;
import edu.vassar.cmpu203.workoutapp.Model.Profile;
import edu.vassar.cmpu203.workoutapp.Model.Strength;
import edu.vassar.cmpu203.workoutapp.Model.Workout;
import edu.vassar.cmpu203.workoutapp.Persistence.FirestoreFacade;
import edu.vassar.cmpu203.workoutapp.Persistence.IPersistenceFacade;


public class FilterFeedInstTest extends AddMiscThings{

    protected IPersistenceFacade persistenceFacade = new FirestoreFacade();

    @org.junit.Rule
    public ActivityScenarioRule<MainActivity> activityRule = new ActivityScenarioRule<>(MainActivity.class);

    /**
     * Adds three posts, and then specifies a length filter that produces a feed with
     * only the desired length
     */
    @Test
    public void FilterLengthTest() {

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
        workout1 = addWorkoutTests(2, workout1, values1, "20", 2, "Lots and lots of push-ups", "None");
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

        //Clicks filter button
        ViewInteraction filterButtonVi = onView(withId(R.id.FeedFilter));
        filterButtonVi.perform(ViewActions.click());

        //Enter length filter
        ViewInteraction filterLengthVi = onView(withId(R.id.LengthInput));
        filterLengthVi.perform(ViewActions.typeText("20"));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());


        //Set filters
        onView(withId(R.id.FilterSet)).perform(ViewActions.click());

        //Check if only second post is shown
        onView(withSubstring("My arms are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));

        //Remove Filters and check if all posts are there
        onView(withId(R.id.RemoveFilter)).perform(ViewActions.click());
        onView(withSubstring("Did some sprints today")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        onView(withSubstring("My arms are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));
        onView(withSubstring("My legs are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

    /**
     * Adds three posts, and then specifies a difficulty filter that produces a feed with
     * only the desired difficulty
     */
    @Test
    public void FilterDifficultyTest() {
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
        workout2 = addWorkoutTests(2, workout2, values2, "30", 3, "Some good weights and what not", "None");
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

        //Clicks filter button
        ViewInteraction filterButtonVi = onView(withId(R.id.FeedFilter));
        filterButtonVi.perform(ViewActions.click());


        //enters the difficulty filter
        ViewInteraction filterDifficultyVi = onView(withId(R.id.diffOptions));
        filterDifficultyVi.perform(ViewActions.click());
        onData(anything()).atPosition(3).perform(ViewActions.click());
        filterDifficultyVi.check(ViewAssertions.matches(ViewMatchers.withSpinnerText("3")));

        //Set filters
        onView(withId(R.id.FilterSet)).perform(ViewActions.click());

        //Check if only second post is shown
        onView(withSubstring("My legs are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        //Remove Filters and check if all posts are there
        onView(withId(R.id.RemoveFilter)).perform(ViewActions.click());
        onView(withSubstring("Did some sprints today")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        onView(withSubstring("My arms are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));
        onView(withSubstring("My legs are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();

    }

    /**
     * Adds two posts, and then specifies a sport filter that produces a feed with
     * only the desired sport focus
     */
    @Test
    public void FilterSportTest() {
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
        workout = addWorkoutTests(1, workout,values, "100", 1, "Five sprints", "Tennis");
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
        workout2 = addWorkoutTests(2, workout2, values2, "30", 3, "Some good weights and what not", "None");
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

        //Clicks filter button
        ViewInteraction filterButtonVi = onView(withId(R.id.FeedFilter));
        filterButtonVi.perform(ViewActions.click());


        //enters the sport filter
        ViewInteraction filterSportVi = onView(withId(R.id.sportOptions));
        filterSportVi.perform(ViewActions.click());
        onData(anything()).atPosition(1).perform(ViewActions.click());
        filterSportVi.check(ViewAssertions.matches(ViewMatchers.withSpinnerText("Tennis")));

        //Set filters
        onView(withId(R.id.FilterSet)).perform(ViewActions.click());

        //Check if only second post is shown
        onView(withSubstring("Did some sprints today")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));

        //Remove Filters and check if all posts are there
        onView(withId(R.id.RemoveFilter)).perform(ViewActions.click());
        onView(withSubstring("Did some sprints today")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        onView(withSubstring("My arms are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));
        onView(withSubstring("My legs are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();

    }

    /**
     * Adds three posts, and then specifies a type filter that produces a feed with
     * only the desired workout type
     */
    @Test
    public void FilterTypeTest() {
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
        workout2 = addWorkoutTests(2, workout2, values2, "30", 3, "Some good weights and what not", "None");
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

        //Clicks filter button
        ViewInteraction filterButtonVi = onView(withId(R.id.FeedFilter));
        filterButtonVi.perform(ViewActions.click());


        //enters the Type filter
        ViewInteraction filterTypeVi = onView(withId(R.id.TypeOptions));
        filterTypeVi.perform(ViewActions.click());
        onData(anything()).atPosition(1).perform(ViewActions.click());
        filterTypeVi.check(ViewAssertions.matches(ViewMatchers.withSpinnerText("Cardio")));

        //Set filters
        onView(withId(R.id.FilterSet)).perform(ViewActions.click());

        //Check if only first post is shown
        onView(withSubstring("Did some sprints today")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));

        //Remove Filters and check if all posts are there
        onView(withId(R.id.RemoveFilter)).perform(ViewActions.click());
        onView(withSubstring("Did some sprints today")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        onView(withSubstring("My arms are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));
        onView(withSubstring("My legs are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();

    }

    /**
     * Adds two posts, and then specifies a length and difficulty filter that produces a feed with
     * only the desired length and difficulty
     */

    @Test
    public void FilterLengthAndDifficulty() {
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
        workout2 = addWorkoutTests(2, workout2, values2, "4", 3, "Some good weights and what not", "None");
        post2.setWorkout(workout2);

        //check that the workout is displaying in the add post screen as intended
        postWorkoutVi.check(ViewAssertions.matches(ViewMatchers.withText(workout2.toString())));

        //create post screen, check to see of default text for caption is there
        //enter new caption
        postCapEdVi.perform(ViewActions.replaceText("My legs are sore"));

        //click the caption button and checks to see that the caption text has changed
        captionButtonVi.perform(ViewActions.click());
        post2.addCaption("My legs are sore");

        // clicks on the post button
        postButtonVi.perform(ViewActions.click());

        //checks that all of the posts are displaying like they are supposed to
        post1Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        post2Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));
        ViewInteraction post3Vi = Espresso.onView(ViewMatchers.withSubstring("My legs are sore"));
        post3Vi.check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        //Clicks filter button
        ViewInteraction filterButtonVi = onView(withId(R.id.FeedFilter));
        filterButtonVi.perform(ViewActions.click());

        //Enter length filter
        ViewInteraction filterLengthVi = onView(withId(R.id.LengthInput));
        filterLengthVi.perform(ViewActions.typeText("4"));
        onView(ViewMatchers.isRoot()).perform(ViewActions.closeSoftKeyboard());

        //enters the difficulty filter
        ViewInteraction filterDifficultyVi = onView(withId(R.id.diffOptions));
        filterDifficultyVi.perform(ViewActions.click());
        onData(anything()).atPosition(3).perform(ViewActions.click());
        filterDifficultyVi.check(ViewAssertions.matches(ViewMatchers.withSpinnerText("3")));

        //Set filters
        onView(withId(R.id.FilterSet)).perform(ViewActions.click());

        onView(withSubstring("My legs are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        //Remove Filters and check if all posts are there
        onView(withId(R.id.RemoveFilter)).perform(ViewActions.click());
        onView(withSubstring("Did some sprints today")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post.toString())));
        onView(withSubstring("My arms are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post1.toString())));
        onView(withSubstring("My legs are sore")).check(ViewAssertions.matches(ViewMatchers.withSubstring(post2.toString())));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

    /**
     * Test to make sure that the snackbar will pop up
     * when length is left empty
     */
    @Test
    public void testFilterSnackbar() {
        SystemClock.sleep(10000);
        Profile profile = createProfile("Username", "Password", "Bio");
        SystemClock.sleep(5000);

        ViewInteraction feedVi = Espresso.onView(ViewMatchers.withId(R.id.FeedFilter));
        feedVi.perform(ViewActions.click());

        //Enter a blank length input
        ViewInteraction lengthVi = Espresso.onView(ViewMatchers.withId(R.id.LengthInput));
        lengthVi.perform(ViewActions.replaceText(""));
        ViewInteraction setButtonVi = Espresso.onView(ViewMatchers.withId(R.id.FilterSet));
        setButtonVi.perform(ViewActions.click());

        //the snackbar matcher
        Matcher<View> snackbarMatcher = ViewMatchers.withText("Input for length mandatory!");
        ViewInteraction snackBarVi = Espresso.onView(snackbarMatcher);
        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));


        //Test for a non-numeric input
        lengthVi.perform(ViewActions.replaceText("This shouldn't be here"));
        setButtonVi.perform(ViewActions.click());

        //the snackbar matcher
        snackbarMatcher = ViewMatchers.withText("Please enter a number for length");
        snackBarVi = Espresso.onView(snackbarMatcher);
        snackBarVi.check(ViewAssertions.matches(snackbarMatcher));

        persistenceFacade.removeUser(profile);
        persistenceFacade.removePosts();
    }

}
