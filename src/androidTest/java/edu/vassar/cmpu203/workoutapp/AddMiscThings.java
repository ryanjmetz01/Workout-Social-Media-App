package edu.vassar.cmpu203.workoutapp;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.anything;

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

import org.hamcrest.Matcher;

import edu.vassar.cmpu203.workoutapp.Model.Cardio;
import edu.vassar.cmpu203.workoutapp.Model.Mobility;
import edu.vassar.cmpu203.workoutapp.Model.Post;
import edu.vassar.cmpu203.workoutapp.Model.Profile;
import edu.vassar.cmpu203.workoutapp.Model.Strength;
import edu.vassar.cmpu203.workoutapp.Model.Workout;
import edu.vassar.cmpu203.workoutapp.Persistence.FirestoreFacade;
import edu.vassar.cmpu203.workoutapp.Persistence.IPersistenceFacade;
import edu.vassar.cmpu203.workoutapp.Controller.MainActivity;



public class AddMiscThings {

    protected IPersistenceFacade persistenceFacade = new FirestoreFacade();

    /**
     * navigates the add workout screens and creates a workout to help with the testing
     * makes it easier to create different types of workouts
     *
     * @param type the type of workout
     * @param workout the workout that is being worked on for comparison
     * @param values the values for the workout, determines which radio buttons are pressed
     * @param length the length of the workout being created
     * @param difficulty the difficulty of the workout being created
     * @param description the description of the workout being created
     * @return a workout that is going to be used for comparison to make sure the app is displaying the data properly
     */
    public static Workout addWorkoutTests(int type, Workout workout, boolean[] values, String length, int difficulty, String description, String sport) {
        //clicks the add workout button on the create post screen
        ViewInteraction addWorkoutButtonVi = Espresso.onView((ViewMatchers.withId(R.id.addWorkoutButton)));
        addWorkoutButtonVi.perform(ViewActions.click());

        //if the workout type is a cardio workout and adds the correct values
        if(type == 1) {
            ViewInteraction cardioButtonVi = Espresso.onView(ViewMatchers.withId(R.id.CardioButton));
            cardioButtonVi.perform(ViewActions.click());


            if(values[0]){
                ViewInteraction agilityVi = Espresso.onView(ViewMatchers.withId(R.id.AgilityOption));
                agilityVi.perform(ViewActions.click());
            }

            if(values[1]){
                ViewInteraction enduranceButtonVi = Espresso.onView(ViewMatchers.withId(R.id.EnduranceOption));
                enduranceButtonVi.perform(ViewActions.click());
            }

            if(values[2]) {
                ViewInteraction speedButtonVi = Espresso.onView(ViewMatchers.withId(R.id.SpeedOption));
                speedButtonVi.perform(ViewActions.click());
            }

            ViewInteraction setVi = Espresso.onView(ViewMatchers.withId(R.id.CardioSetButton));
            setVi.perform(ViewActions.click());
            workout = new Cardio(values);
        }
        // if the workout is a strength workout
        else if (type == 2){
            ViewInteraction strengthButtonVi = Espresso.onView(ViewMatchers.withId(R.id.StrengthButton));
            strengthButtonVi.perform(ViewActions.click());

            if(values[0]){
                ViewInteraction upperBodyButtonVi = Espresso.onView(ViewMatchers.withId(R.id.UpperBodyOption));
                upperBodyButtonVi.perform(ViewActions.click());
            }

            if(values[1]){
                ViewInteraction lowerBodyButtonVi = Espresso.onView(ViewMatchers.withId(R.id.LowerBodyOption));
                lowerBodyButtonVi.perform(ViewActions.click());
            }

            if(values[2]) {
                ViewInteraction bodyweightButtonVi = Espresso.onView(ViewMatchers.withId(R.id.BodyWeightOption));
                bodyweightButtonVi.perform(ViewActions.click());
            }

            if(values[3]){
                ViewInteraction fullBodyButtonVi = Espresso.onView(ViewMatchers.withId(R.id.FullBodyOption));
                fullBodyButtonVi.perform(ViewActions.click());
            }

            ViewInteraction setButton = Espresso.onView(ViewMatchers.withId(R.id.StrengthSetButton));
            setButton.perform(ViewActions.click());
            workout = new Strength(values);
        }
        else {
            ViewInteraction mobilityButtonVi = Espresso.onView(ViewMatchers.withId(R.id.MobilityButton));
            mobilityButtonVi.perform(ViewActions.click());

            if(values[0]){
                ViewInteraction StaticButtonVi = Espresso.onView(ViewMatchers.withId(R.id.StaticOption));
                StaticButtonVi.perform(ViewActions.click());
            }

            if(values[1]){
                ViewInteraction DynamicButtonVi = Espresso.onView(ViewMatchers.withId(R.id.DynamicOption));
                DynamicButtonVi.perform(ViewActions.click());
            }

            if(values[2]) {
                ViewInteraction YogaButtonVi = Espresso.onView(ViewMatchers.withId(R.id.YogaOption));
                YogaButtonVi.perform(ViewActions.click());
            }

            ViewInteraction setButton = Espresso.onView(ViewMatchers.withId(R.id.MobilitySetButton));
            setButton.perform(ViewActions.click());
            workout = new Mobility(values);
        }

        // add the length
        ViewInteraction lengthVi = Espresso.onView(ViewMatchers.withId(R.id.WorkoutLengthInput));
        lengthVi.perform(ViewActions.replaceText(length));
        workout.setLength(Integer.parseInt(length));

        //add the difficulty
        ViewInteraction difficultyVi = Espresso.onView(ViewMatchers.withId(R.id.WorkoutDifficultyInput));
        difficultyVi.perform(setProgress(difficulty));
        workout.setDifficulty(difficulty);

        // add the description
        ViewInteraction workoutDescriptionVi = Espresso.onView(ViewMatchers.withId(R.id.WorkoutDescriptionInput));
        workoutDescriptionVi.perform(ViewActions.replaceText(description));
        workout.setDescription(description);

        //set sport
        ViewInteraction SportVi = onView(withId(R.id.spinner2));
        SportVi.perform(ViewActions.click());
        onData(anything()).atPosition(getSportPos(sport)).perform(ViewActions.click());
        workout.setSport(sport);

        //click the create workout button on the add workout screen
        ViewInteraction createWorkoutButtonVi = Espresso.onView(ViewMatchers.withId(R.id.AddWorkoutButton));
        createWorkoutButtonVi.perform(ViewActions.click());


        return workout;

    }

    //helps set the seekbar to different values
    public static ViewAction setProgress(final int progress) {
        return new ViewAction() {
            @Override
            public void perform(UiController uiController, View view) {
                SeekBar seekBar = (SeekBar) view;
                seekBar.setProgress(progress);
            }
            @Override
            public String getDescription() {
                return "Set a progress on a SeekBar";
            }
            @Override
            public Matcher<View> getConstraints() {
                return ViewMatchers.isAssignableFrom(SeekBar.class);
            }
        };
    }

    /**
     * navigates the create profile screen
     * @return a default profile to be used to testing
     */
    public static Profile createProfile(String user, String password, String bio) {
        ViewInteraction signUpButtonVi = Espresso.onView(ViewMatchers.withId(R.id.signUpButton));
        signUpButtonVi.perform(ViewActions.click());
        ViewInteraction usernameTextField = Espresso.onView(ViewMatchers.withId(R.id.UsernameText));
        usernameTextField.perform(ViewActions.replaceText(user));
        ViewInteraction passwordTextField = Espresso.onView(ViewMatchers.withId(R.id.passwordEditText));
        passwordTextField.perform(ViewActions.replaceText(password));
        ViewInteraction bioTextField = Espresso.onView(ViewMatchers.withId(R.id.bioEditText));
        bioTextField.perform(ViewActions.replaceText(bio));
        ViewInteraction createButtonVi = Espresso.onView(ViewMatchers.withId(R.id.createButton));
        createButtonVi.perform(ViewActions.click());
        Profile profile = new Profile();
        profile.setUsername(user);
        profile.setPasswordFromString(password);
        profile.setBio(bio);

        return profile;
    }

    /**
     * logs into the system using the given username and password
     * @param username the username of the profile logging in
     * @param password the password of the profile logging in
     */
    public static void logIn(String username, String password){
        ViewInteraction usernameInput = Espresso.onView(ViewMatchers.withId(R.id.usernameField));
        usernameInput.perform(ViewActions.replaceText(username));
        ViewInteraction passwordInput = Espresso.onView(ViewMatchers.withId(R.id.passwordField));
        passwordInput.perform(ViewActions.replaceText(password));
        ViewInteraction logInButton = Espresso.onView(ViewMatchers.withId(R.id.logInButton));
        logInButton.perform(ViewActions.click());


    }

    public static int getSportPos(String sport) {
        if (sport.equals("None"))
            return 0;
        else if (sport.equals("Tennis"))
            return 1;
        else if (sport.equals("Lacrosse"))
            return 2;
        else if (sport.equals("Baseball"))
            return 3;
        else if (sport.equals("Soccer"))
            return 4;
        else if (sport.equals("Track and Field"))
            return 5;
        else if (sport.equals("Basketball"))
            return 6;
        else if (sport.equals("Rugby"))
            return 7;
        else if (sport.equals("Rowing"))
            return 8;
        else
            return 9;
    }

}
