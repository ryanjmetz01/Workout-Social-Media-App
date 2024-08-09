package edu.vassar.cmpu203.workoutapp.View;

import edu.vassar.cmpu203.workoutapp.Model.Feed;

public interface ICreateProfileView {

    interface Listener{
/*        void onAddedUsername(String username, ICreateProfileView createProfileView);
        void onAddedPassword(String password, ICreateProfileView createProfileView);
        void onAddedBio(String bio, ICreateProfileView createProfileView);*/
        void onCreateButton(String username, String password, String bio, ICreateProfileView createProfileView);
    }

    void onCreateSuccess();
    void onUserAlreadyExists();
    //void onProfileUpdated(Feed feed);
}
