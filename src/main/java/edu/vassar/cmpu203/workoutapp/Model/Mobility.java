package edu.vassar.cmpu203.workoutapp.Model;

import java.io.Serializable;

public class Mobility extends Workout implements Serializable {
    private boolean StaticStretching = false;
    private boolean DynamicStretching = false;
    private boolean Yoga = false;

    public Mobility() {}

    public Mobility(boolean[] Attributes) {
        StaticStretching = Attributes[0];
        DynamicStretching = Attributes[1];
        Yoga = Attributes[2];
    }

    public Mobility(Workout workout){
        this.description = workout.description;
        this.difficulty = workout.difficulty;
        this.length = workout.length;
        this.sportFocus = workout.sportFocus;
        this.workoutType = workout.workoutType;
    }

    public void setStaticFocus(boolean staticStretching) {
        StaticStretching = staticStretching;
    }

    public void setDynamicFocus(boolean dynamicStretching) { DynamicStretching = dynamicStretching; }

    public void setYogaFocus(boolean yoga) {
        Yoga = yoga;
    }

    @Override
    public String toString() {
        return description + "\n\n" + "Length: " + this.length + ", Difficulty: " + this.difficulty + ", Sport Focus: " + this.sportFocus + "\nStatic Stretching: " + StaticStretching
                + "\nDynamic Stretching: " + DynamicStretching + "\nYoga: " + Yoga;
    }

    public boolean isStaticStretching() {
        return StaticStretching;
    }

    public boolean isDynamicStretching() {
        return DynamicStretching;
    }

    public boolean isYoga() {
        return Yoga;
    }
}
