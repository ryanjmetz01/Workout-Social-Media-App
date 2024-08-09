package edu.vassar.cmpu203.workoutapp.View;

public interface IFilterView {
    interface Listener {
       void onSetFilter(int length, int difficulty, int workoutType, String sport);
    }
}
