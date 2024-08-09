package edu.vassar.cmpu203.workoutapp.View;

public interface IHomeScreenView {

    interface Listener{
        void onLogIn(String username, String password, IHomeScreenView homeScreenView);
        void onSignUp();
    }

    void onInvalidCredentials();
    void successfulLogIn();
}
