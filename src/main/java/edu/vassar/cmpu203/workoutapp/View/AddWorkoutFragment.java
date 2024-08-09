package edu.vassar.cmpu203.workoutapp.View;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.workoutapp.Model.*;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentCreatePostBinding;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentAddWorkoutBinding;


import edu.vassar.cmpu203.workoutapp.R;

public class AddWorkoutFragment extends Fragment implements IAddWorkout {

    private FragmentAddWorkoutBinding binding;
    private Listener listener;
    private boolean[] WorkoutAttributes;
    private int workoutType = 0;
    int workoutLength = 0;
    private Workout workout;
    private Post post;
    int signal = 0;


    private boolean workoutSet = false;
    private final static String WRK_SET = "WRK_SET";

    public AddWorkoutFragment(Listener listener) {

        this.listener = listener;
        this.workout = listener.getCurWorkout();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.binding = FragmentAddWorkoutBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        onWorkoutSelected(workout.getWorkoutType());

        if (workout.getLength() != 0)
            this.binding.WorkoutLengthInput.setText(workout.getLength() + "");
        this.binding.WorkoutDifficultyInput.setProgress(workout.getDifficulty());
        this.binding.WorkoutDescriptionInput.setText(workout.getDescription());
        this.binding.spinner2.setSelection(getSportPos(workout.getSportFocus()));

        // onViewCreated is responsible for wiring up the event handlers

        // add listener to be called when the add button is pressed

        this.workout = this.listener.getCurWorkout();

        /**
         * sets the click of the add workout button, makes sure that we have all needed data and the
         * data has been inputted normally
         */
        this.binding.AddWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable workoutLengthEditable = AddWorkoutFragment.this.binding.WorkoutLengthInput.getText();
                String workoutLengthStr = workoutLengthEditable.toString();

                Editable workoutDescEditable = AddWorkoutFragment.this.binding.WorkoutDescriptionInput.getText();
                String workoutDescStr = workoutDescEditable.toString();

                int workoutDifficulty = binding.WorkoutDifficultyInput.getProgress();

                String workoutSport = binding.spinner2.getSelectedItem().toString();


                // confirm we have both name and qty
                if (workoutLengthStr.length() == 0 || workoutDifficulty == 0 || workoutDescStr.length() == 0){
                    Snackbar.make(v, "Length, difficulty and description are mandatory!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                try {
                    workoutLength = Integer.parseInt(workoutLengthStr);
                } catch (NumberFormatException e) {
                    Snackbar.make(v, "Please enter a number for length", Snackbar.LENGTH_LONG).show();
                    return;
                }

                workoutLengthEditable.clear();
                workoutDescEditable.clear();




                // let view listener know that it should add a new workout
                if(workout.getWorkoutType() == 1 || workout.getWorkoutType() == 2 || workout.getWorkoutType() == 3) {
                    AddWorkoutFragment.this.listener.onAddedWorkout(workoutLength, workoutDifficulty, workoutDescStr, workoutSport);
                    workoutSet = true;
                }
                else
                    Snackbar.make(v, "Choosing a workout type is mandatory!", Snackbar.LENGTH_LONG).show();

            }
        });

        /**
         * sets the click for the cardio workout button
         */
        this.binding.CardioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable workoutLengthEditable = AddWorkoutFragment.this.binding.WorkoutLengthInput.getText();
                String workoutLengthStr = workoutLengthEditable.toString();

                Editable workoutDescEditable = AddWorkoutFragment.this.binding.WorkoutDescriptionInput.getText();
                String workoutDescStr = workoutDescEditable.toString();

                int workoutDifficulty = binding.WorkoutDifficultyInput.getProgress();

                String workoutSport = binding.spinner2.getSelectedItem().toString();

                try {
                    workoutLength = Integer.parseInt(workoutLengthStr);
                } catch (NumberFormatException e) {
                    workoutLength = 0;
                }

                workoutLengthEditable.clear();
                workoutDescEditable.clear();


                AddWorkoutFragment.this.listener.CardioButton(workoutLength, workoutDifficulty, workoutDescStr, workoutSport);

            }
        });

        /**
         * sets the click for the strength workout button
         */
        this.binding.StrengthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable workoutLengthEditable = AddWorkoutFragment.this.binding.WorkoutLengthInput.getText();
                String workoutLengthStr = workoutLengthEditable.toString();

                Editable workoutDescEditable = AddWorkoutFragment.this.binding.WorkoutDescriptionInput.getText();
                String workoutDescStr = workoutDescEditable.toString();

                int workoutDifficulty = binding.WorkoutDifficultyInput.getProgress();

                String workoutSport = binding.spinner2.getSelectedItem().toString();

                try {
                    workoutLength = Integer.parseInt(workoutLengthStr);
                } catch (NumberFormatException e) {
                    workoutLength = 0;
                }

                workoutLengthEditable.clear();
                workoutDescEditable.clear();

                AddWorkoutFragment.this.listener.StrengthButton(workoutLength, workoutDifficulty, workoutDescStr, workoutSport);

            }
        });

        /**
         * sets the click for the mobility workout button
         */
        this.binding.MobilityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable workoutLengthEditable = AddWorkoutFragment.this.binding.WorkoutLengthInput.getText();
                String workoutLengthStr = workoutLengthEditable.toString();

                Editable workoutDescEditable = AddWorkoutFragment.this.binding.WorkoutDescriptionInput.getText();
                String workoutDescStr = workoutDescEditable.toString();

                int workoutDifficulty = binding.WorkoutDifficultyInput.getProgress();

                String workoutSport = binding.spinner2.getSelectedItem().toString();

                try {
                    workoutLength = Integer.parseInt(workoutLengthStr);
                } catch (NumberFormatException e) {
                    workoutLength = 0;
                }

                workoutLengthEditable.clear();
                workoutDescEditable.clear();


                AddWorkoutFragment.this.listener.MobilityButton(workoutLength, workoutDifficulty, workoutDescStr, workoutSport);
            }
        });

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(WRK_SET, this.workoutSet);

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null){
            this.workoutSet = savedInstanceState.getBoolean(WRK_SET);

        }

        if(workoutSet) {
           onWorkoutSelected(workoutType);
        }

    }

    /**
     * sets the functionality of the buttons based on the workout type
     * @param workoutType the type of the workout
     */
    @Override
    public void onWorkoutSelected(int workoutType) {
        if (workoutType == 0) {
            this.binding.CardioButton.setBackgroundColor(Color.BLUE);
            this.binding.StrengthButton.setBackgroundColor(Color.BLUE);
            this.binding.MobilityButton.setBackgroundColor(Color.BLUE);
        }
        if (workoutType == 1) {
            this.binding.CardioButton.setBackgroundColor(Color.BLUE);
            this.binding.StrengthButton.setEnabled(false);
            this.binding.MobilityButton.setEnabled(false);
        }
        if (workoutType == 02) {
            this.binding.CardioButton.setEnabled(false);
            this.binding.StrengthButton.setBackgroundColor(Color.BLUE);
            this.binding.MobilityButton.setEnabled(false);
        }
        if (workoutType == 3) {
            this.binding.CardioButton.setEnabled(false);
            this.binding.StrengthButton.setEnabled(false);
            this.binding.MobilityButton.setBackgroundColor(Color.BLUE);
        }
    }

    /**
     * helps save the data of the spinner
     * @param sport the sport that someone has selected as the focus
     * @return an int that represents the number of the option of the spinner
     */
    public int getSportPos(String sport) {
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