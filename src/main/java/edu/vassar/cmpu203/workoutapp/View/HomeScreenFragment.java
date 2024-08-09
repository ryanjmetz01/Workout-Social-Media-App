package edu.vassar.cmpu203.workoutapp.View;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import edu.vassar.cmpu203.workoutapp.R;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentHomeScreenBinding;


public class HomeScreenFragment extends Fragment implements IHomeScreenView{

    private FragmentHomeScreenBinding binding;
    private IHomeScreenView.Listener listener;

    public HomeScreenFragment(Listener listener) {
        this.listener = listener;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       this.binding = FragmentHomeScreenBinding.inflate(inflater);
       return this.binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        /**
         * sets the click for the login button, will display a message if someone does
         * not enter all the required information
         */
        this.binding.logInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Editable usernameEditable = HomeScreenFragment.this.binding.usernameField.getText();
                String username = usernameEditable.toString();

                Editable passwordEditable = HomeScreenFragment.this.binding.passwordField.getText();
                String password = passwordEditable.toString();

                if (username.length() == 0 || password.length() == 0) {
                    Snackbar.make(view,"Please enter both your username and password", Snackbar.LENGTH_LONG).show();
                    return;
                }

                usernameEditable.clear();
                passwordEditable.clear();

                HomeScreenFragment.this.listener.onLogIn(username, password, HomeScreenFragment.this);
            }
        });

        /**
         * sets the click for the sign up button
         */
        this.binding.signUpButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                HomeScreenFragment.this.listener.onSignUp();
            }
        });
    }

    /**
     * displays a message when someone logs in with invalid credentials
     */
    @Override
    public void onInvalidCredentials(){
        displayMessage(R.string.invalidCredentials);
    }

    /**
     * displays a message when someone logs in successfully
     */
    @Override
    public void successfulLogIn(){displayMessage(R.string.login);}

    /**
     * displays a message as a snackbar
     * @param msgRid the location in string.xml of th desired string
     */
    private void displayMessage(int msgRid){
        Snackbar.make(this.binding.getRoot(), msgRid, Snackbar.LENGTH_LONG).show();
    }
}