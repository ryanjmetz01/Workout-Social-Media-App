package edu.vassar.cmpu203.workoutapp.View;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;

public interface IMainView {

    public View getRootView();
    public void displayFragment(Fragment fragment, boolean allowBack);
    void displayFragment(Class<? extends  Fragment> fragment, Bundle fragArgs, boolean b);
   Fragment getCurrentFragment();
}
