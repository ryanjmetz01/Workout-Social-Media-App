package edu.vassar.cmpu203.workoutapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.vassar.cmpu203.workoutapp.Model.*;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentCreatePostBinding;


public class Create_Post_Fragment extends Fragment implements ICreatePostView {

    private FragmentCreatePostBinding binding;
    private ICreatePostView.Listener listener;
    private Workout w;

    public Create_Post_Fragment(Listener listener) {
        this.listener = listener;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentCreatePostBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){

        w = this.listener.getCurWorkout();

        // if the workout does not have any values yet
        if(w.length == 0) {
            this.binding.postWorkout.setText("No workout added");
        }
        else
            this.binding.postWorkout.setText(w.toString());

        /**
         * when clicking on the caption button, will add caption to post
         * based on what is in the caption text box
         */
        this.binding.captionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                Editable postCaptionEditable = Create_Post_Fragment.this.binding.captionTextBox.getText();
                String postCaption = postCaptionEditable.toString();

                postCaptionEditable.clear();

                Create_Post_Fragment.this.listener.onAddedCaption(postCaption, Create_Post_Fragment.this);


            }
        });

        /**
         * sets click of add Workout button, will take user to add workout screen
         */
        this.binding.addWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Create_Post_Fragment.this.listener.onWorkoutButton();

            }

        });

        /**
         * sets the click for the post button, will post the new post
         */
        this.binding.postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Create_Post_Fragment.this.listener.onPostButton();
            }
        });

        /**
         * sets the click for the cancel button, takes user back to feed
         */
        this.binding.cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Create_Post_Fragment.this.listener.onCancelButton();
            }
        });


    }

    /**
     * updates the caption in the workout preview
     * @param caption the new caption
     */
    @Override
    public void updateCaption(String caption) {

        this.binding.postCaption.setText(caption);
    }

}