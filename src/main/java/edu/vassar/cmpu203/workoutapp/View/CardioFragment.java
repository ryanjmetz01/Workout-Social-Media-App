package edu.vassar.cmpu203.workoutapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.workoutapp.Controller.MainActivity;
import edu.vassar.cmpu203.workoutapp.Model.Post;
import edu.vassar.cmpu203.workoutapp.Model.Workout;
import edu.vassar.cmpu203.workoutapp.R;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentCardioBinding;


public class CardioFragment extends Fragment implements IWorkoutType {

    private FragmentCardioBinding binding;
    private IWorkoutType.Listener listener;
    private boolean[] AttributeList  = new boolean[3];

    public CardioFragment(Listener listener){
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.binding = FragmentCardioBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        /**
         * when set button clicked sets the attributes of the workout based on
         * if the radio buttons are clicked
         */
        this.binding.CardioSetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // retrieve item's name
                AttributeList[0] = CardioFragment.this.binding.AgilityOption.isChecked();
                AttributeList[1] = CardioFragment.this.binding.EnduranceOption.isChecked();
                AttributeList[2] = CardioFragment.this.binding.SpeedOption.isChecked();


                // let view listener know that it should add a new workout
                CardioFragment.this.listener.onAddedAttributes(AttributeList, 1);
            }
        });
    }

}