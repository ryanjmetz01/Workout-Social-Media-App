package edu.vassar.cmpu203.workoutapp.View;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.workoutapp.Model.Post;

public interface IWorkoutType {


    interface Listener{
        void onAddedAttributes(boolean[] Attributes, int workoutType);
    }
}
