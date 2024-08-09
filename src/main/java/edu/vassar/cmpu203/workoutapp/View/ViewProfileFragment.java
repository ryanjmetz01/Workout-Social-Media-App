package edu.vassar.cmpu203.workoutapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.workoutapp.Model.Profile;
import edu.vassar.cmpu203.workoutapp.R;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentCreateProfileBinding;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentStrengthBinding;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentViewProfileBinding;


public class ViewProfileFragment extends Fragment implements IViewProfileView{

    private IViewProfileView.Listener listener;
    private FragmentViewProfileBinding binding;
    private Profile curUser;

    public ViewProfileFragment(Listener listener){
        this.listener = listener;
        this.curUser = this.listener.getCurUser();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentViewProfileBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // set the contents of the screen using the information from the current user
        this.binding.viewUsername.setText(curUser.getUsername());
        this.binding.ProfileViewBio.setText(curUser.getBio());
        this.binding.FollowerDisplay.setText(""+ curUser.getNumFollowers());
        this.binding.FollowingDisplay.setText("" + curUser.getNumFollowing());
        this.binding.PostNumberDisplay.setText("" + curUser.getNumPosts());

        /**
         * sets the click for the edit profile button
         */
        this.binding.editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewProfileFragment.this.listener.onEditProfile();
            }
        });

        /**
         * sets the click for the go back button
         */
        this.binding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewProfileFragment.this.listener.onGoBack();
            }
        });

        /**
         * sets the click for the view follow request button
         */
        this.binding.button5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewProfileFragment.this.listener.onFollowRequests();
            }
        });

        /**
         * sets the click for the logout button
         */
        this.binding.LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { ViewProfileFragment.this.listener.logout(ViewProfileFragment.this); }
        });
    }

    /**
     * displays a message when someone successfully logs out
     */
    @Override
    public void onSuccessfulLogOut(){
        Snackbar.make(this.binding.getRoot(), "Logout successful", Snackbar.LENGTH_LONG).show();
    }

}