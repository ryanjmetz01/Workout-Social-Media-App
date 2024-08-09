package edu.vassar.cmpu203.workoutapp.View;
import android.os.Bundle;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import edu.vassar.cmpu203.workoutapp.databinding.MainBinding;


public class MainView implements IMainView{

    FragmentActivity activity;
    MainBinding binding;

    public MainView(FragmentActivity activity){
        this.activity = activity;
        this.binding = MainBinding.inflate(activity.getLayoutInflater());
    }

    @Override
    public View getRootView() {
        return this.binding.getRoot();
    }

    @Override
    public void displayFragment(Fragment fragment, boolean allowBack) {
        FragmentManager fm = this.activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        ft.replace(this.binding.fragmentContainerView.getId(), fragment);

        if (allowBack) ft.addToBackStack(null);

        ft.commit();
    }

    @Override
    public void displayFragment(Class<? extends Fragment> fragment, Bundle fragArgs, boolean b) {
        FragmentManager fm = this.activity.getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(this.binding.fragmentContainerView.getId(), fragment, fragArgs);
        if (b) ft.addToBackStack(null);
        ft.commit();

    }

    @Override
    public Fragment getCurrentFragment() {
        return this.binding.fragmentContainerView.getFragment();
    }


}
