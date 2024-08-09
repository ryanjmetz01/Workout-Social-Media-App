package edu.vassar.cmpu203.workoutapp.View;

import android.view.View;

import edu.vassar.cmpu203.workoutapp.Model.*;

public interface ICreatePostView {


    interface Listener{
        void onAddedCaption(String caption, ICreatePostView createPostView);
        void onWorkoutButton();
        void onPostButton();
        void onCancelButton();
        Workout getCurWorkout();
    }
    void updateCaption(String caption);

}
