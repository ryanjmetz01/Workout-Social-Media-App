package edu.vassar.cmpu203.workoutapp.Model;

import java.io.Serializable;

public class Cardio extends Workout implements Serializable {
    private boolean EnduranceFocus = false;
    private boolean AgilityFocus = false;
    private boolean SpeedFocus = false;

    public Cardio() {}

    public Cardio(boolean[] Attributes) {
        AgilityFocus = Attributes[0];
        EnduranceFocus = Attributes[1];
        SpeedFocus = Attributes[2];
    }


    public Cardio(Workout workout){
        this.description = workout.description;
        this.difficulty = workout.difficulty;
        this.length = workout.length;
        this.sportFocus = workout.sportFocus;
        this.workoutType = workout.workoutType;


    }


    public void setAgilityFocus(boolean agilityFocus) {
        AgilityFocus = agilityFocus;
    }

    public void setEnduranceFocus(boolean enduranceFocus) {
        EnduranceFocus = enduranceFocus;
    }

    public void setSpeedFocus(boolean speedFocus) {
        SpeedFocus = speedFocus;
    }

    @Override
    public String toString() {
        return description + "\n\n" + "Length: " + this.length + ", Difficulty: " + this.difficulty + ", Sport Focus: " + this.sportFocus + "\nEndurance Focus: " + EnduranceFocus
                + "\nAgility Focus: " + AgilityFocus + "\nSpeed Focus: " + SpeedFocus;
    }

    public boolean isEnduranceFocus() {
        return EnduranceFocus;
    }

    public boolean isAgilityFocus() {
        return AgilityFocus;
    }

    public boolean isSpeedFocus() {
        return SpeedFocus;
    }
}
