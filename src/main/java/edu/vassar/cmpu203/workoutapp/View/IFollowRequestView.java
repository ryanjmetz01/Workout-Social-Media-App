package edu.vassar.cmpu203.workoutapp.View;

import edu.vassar.cmpu203.workoutapp.Model.Profile;

public interface IFollowRequestView {

    interface Listener{
        void onBack();
        void onAccept(Profile profile);
        void onDecline(Profile profile);
        void onAddedFollower(Profile profile);
        Profile getCurUser();
    }
}
