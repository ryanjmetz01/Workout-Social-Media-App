package edu.vassar.cmpu203.workoutapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.workoutapp.R;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentCreateProfileBinding;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentEditProfileBinding;


public class EditProfileFragment extends Fragment implements IEditProfileView {

    private FragmentEditProfileBinding binding;
    private IEditProfileView.Listener listener;

    public EditProfileFragment(Listener listener) {
        this.listener = listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentEditProfileBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){


        /**
         * sets the click for the password set button, makes sure that it is not empty
         */
        this.binding.setPasswordButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //For Password
                Editable profilePasswordEditable = EditProfileFragment.this.binding.EditPasswordText.getText();
                String password = profilePasswordEditable.toString();

                profilePasswordEditable.clear();

                if (password.length() == 0){
                    Snackbar.make(v, "Can't have an empty Password!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                EditProfileFragment.this.listener.onEditedPassword(password, EditProfileFragment.this);
            }
        });

        /**
         * sets the click for the bio set button, makes sure that it is not empty
         */
        this.binding.setBioButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //For Bio
                Editable profileBioEditable = EditProfileFragment.this.binding.EditBioText.getText();
                String bio = profileBioEditable.toString();

                profileBioEditable.clear();

                if (bio.length() == 0){
                    Snackbar.make(v, "Can't have an empty bio!", Snackbar.LENGTH_LONG).show();
                    return;
                }

                EditProfileFragment.this.listener.onEditedBio(bio, EditProfileFragment.this);
            }
        });

        /**
         * sets the click for the done button, will take them back to the feed
         */
        this.binding.doneButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                EditProfileFragment.this.listener.onDoneButton();
            }
        });
    }

}