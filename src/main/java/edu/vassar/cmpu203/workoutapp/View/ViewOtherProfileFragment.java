package edu.vassar.cmpu203.workoutapp.View;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.workoutapp.Model.Profile;
import edu.vassar.cmpu203.workoutapp.R;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentCreateProfileBinding;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentStrengthBinding;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentViewOtherProfileBinding;


public class ViewOtherProfileFragment extends Fragment implements IViewOtherProfileView{

    private IViewOtherProfileView.Listener listener;
    private FragmentViewOtherProfileBinding binding;
    private Profile profile;
    private boolean request = false;
    private final static String REQUEST = "REQUEST";


    public ViewOtherProfileFragment(Listener listener) {

        this.listener = listener;
        this.profile = this.listener.getUser2();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        this.binding = FragmentViewOtherProfileBinding.inflate(inflater);
        return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        // sets the text of the screen with the information from the user's profile
        this.binding.OtherProfileUsername.setText(profile.getUsername());
        this.binding.OtherProfileViewBio.setText(profile.getBio());
        this.binding.PostNumberDisplay.setText(""+ profile.getNumPosts());
        this.binding.FollowerDisplay.setText(""+profile.getNumFollowers());
        this.binding.FollowingDisplay.setText(""+profile.getNumFollowing());

        /**
         * sets the click for the request follow button, sets the state of the screen
         */
        this.binding.requestFollowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewOtherProfileFragment.this.request = true;
                ViewOtherProfileFragment.this.listener.requestFollow(profile, ViewOtherProfileFragment.this);
            }
        });


        /**
         * sets the click of the back button
         */
        this.binding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewOtherProfileFragment.this.listener.goBack();
            }
        });
    }

    /**
     * saves the state of the screen
     * @param outState the bundle where the state is stored
     */
    @Override
    public void onSaveInstanceState(@Nullable Bundle outState){
        super.onSaveInstanceState(outState);

        outState.putBoolean(REQUEST, request);
    }

    /**
     * controls what happens when the state is recreated
     * @param savedInstanceState the bundle where the state information is coming from
     */
    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState){
        super.onViewStateRestored(savedInstanceState);

        if (savedInstanceState != null){
            this.request = savedInstanceState.getBoolean(REQUEST);
        }

        if(request){
            onRequest();
        }
    }

    /**
     * makes it so that the user cannot click on the follow request button
     */
    @Override
    public void onRequest(){
        this.binding.requestFollowButton.setEnabled(false);
    }

    /**
     * displays a message if a user requests to follow someone they have already requested
     * sets the button to not be enabled
     */
    @Override
    public void onAlreadyRequested(){
        this.binding.requestFollowButton.setEnabled(false);
        Snackbar.make(this.binding.getRoot(), "You already requested this person", Snackbar.LENGTH_LONG).show();
    }

    /**
     * displays a message if someone tries to follow a person that they already follow
     * sets the follow button to not enabled
     */
    @Override
    public void onAlreadyFollowed(){
        this.binding.requestFollowButton.setEnabled(false);
        Snackbar.make(this.binding.getRoot(), "You already follow this profile", Snackbar.LENGTH_LONG).show();
    }
}