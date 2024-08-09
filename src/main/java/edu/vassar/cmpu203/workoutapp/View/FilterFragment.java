package edu.vassar.cmpu203.workoutapp.View;

import android.os.Bundle;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.workoutapp.Model.Feed;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentFilterBinding;

public class FilterFragment extends Fragment implements IFilterView {

    private FragmentFilterBinding binding;
    private Listener listener;
    public FilterFragment(Listener listener) {
        this.listener = listener;
    }
    int workoutLength = 0;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        this.binding = FragmentFilterBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View v, Bundle savedInstanceState ) {

        /**
         * sets the click for the filter set button
         * displays a warning message if the values are not entered in the correct way
         */
        this.binding.FilterSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Editable workoutLengthEditable = FilterFragment.this.binding.LengthInput.getText();
                String workoutLengthStr = workoutLengthEditable.toString();

                // retrieve item's quantity
                String workoutDifficulty = binding.diffOptions.getSelectedItem().toString();
                int workoutType = binding.TypeOptions.getSelectedItemPosition();
                String sport = binding.sportOptions.getSelectedItem().toString();

                int workoutDif = Integer.parseInt(workoutDifficulty);

                // confirm we have both name and qty
                if (workoutLengthStr.length() == 0) {
                    Snackbar.make(v, "Input for length mandatory!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                try {
                    workoutLength = Integer.parseInt(workoutLengthStr);
                } catch (NumberFormatException e) {
                    Snackbar.make(v, "Please enter a number for length", Snackbar.LENGTH_LONG).show();
                    return;
                }

                workoutLengthEditable.clear();

                FilterFragment.this.listener.onSetFilter(workoutLength, workoutDif, workoutType, sport);
            }
        });

    }
}