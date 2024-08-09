package edu.vassar.cmpu203.workoutapp.Controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentFactory;

import edu.vassar.cmpu203.workoutapp.View.AddWorkoutFragment;
import edu.vassar.cmpu203.workoutapp.View.CardioFragment;
import edu.vassar.cmpu203.workoutapp.View.EditProfileFragment;
import edu.vassar.cmpu203.workoutapp.View.FollowRequestFragment;
import edu.vassar.cmpu203.workoutapp.View.StrengthFragment;
import edu.vassar.cmpu203.workoutapp.Model.*;
import edu.vassar.cmpu203.workoutapp.View.CardioFragment;
import edu.vassar.cmpu203.workoutapp.View.CreateProfileFragment;
import edu.vassar.cmpu203.workoutapp.View.Create_Post_Fragment;
import edu.vassar.cmpu203.workoutapp.View.FeedFragment;
import edu.vassar.cmpu203.workoutapp.View.FilterFragment;
import edu.vassar.cmpu203.workoutapp.View.HomeScreenFragment;
import edu.vassar.cmpu203.workoutapp.View.MobilityFragment;
import edu.vassar.cmpu203.workoutapp.View.StrengthFragment;
import edu.vassar.cmpu203.workoutapp.View.ViewOtherProfileFragment;
import edu.vassar.cmpu203.workoutapp.View.ViewProfileFragment;


public class WorkoutAppFragFactory extends FragmentFactory {

    private MainActivity controller;

    public WorkoutAppFragFactory(MainActivity controller) {
        this.controller = controller;
    }


    @NonNull
    @Override
    public Fragment instantiate(@NonNull ClassLoader classLoader, @NonNull String className){

        Class<? extends Fragment> fragClass = loadFragmentClass(classLoader, className);

        if(fragClass == AddWorkoutFragment.class)
            return new AddWorkoutFragment(controller);
        else if(fragClass == FeedFragment.class)
            return new FeedFragment(controller);
        else if(fragClass == Create_Post_Fragment.class)
            return new Create_Post_Fragment(controller);
        else if(fragClass == FilterFragment.class)
            return new FilterFragment(controller);
        else if(fragClass == HomeScreenFragment.class)
            return new HomeScreenFragment(controller);
        else if(fragClass == CreateProfileFragment.class)
            return new CreateProfileFragment(controller);
        else if(fragClass == CardioFragment.class)
            return new CardioFragment(controller);
        else if(fragClass == StrengthFragment.class)
            return new StrengthFragment(controller);
        else if(fragClass == MobilityFragment.class)
            return new MobilityFragment(controller);
        else if(fragClass == ViewProfileFragment.class)
            return new ViewProfileFragment(controller);
        else if(fragClass == FollowRequestFragment.class)
            return new FollowRequestFragment(controller);
        else if (fragClass == ViewOtherProfileFragment.class)
            return new ViewOtherProfileFragment(controller);
        else if (fragClass == EditProfileFragment.class)
            return new EditProfileFragment(controller);
        else
            return super.instantiate(classLoader, className);

    }

}
