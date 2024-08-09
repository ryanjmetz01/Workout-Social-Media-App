package edu.vassar.cmpu203.workoutapp.Model;

import java.io.Serializable;

public class Strength extends Workout implements Serializable {
    private boolean UpperBodyFocus;
    private boolean LowerBodyFocus;
    private boolean FullBodyFocus;
    private boolean BodyWeightFocus;

    public Strength() {}

    public Strength(boolean[] Attributes) {
        UpperBodyFocus = Attributes[0];
        LowerBodyFocus = Attributes[1];
        BodyWeightFocus = Attributes[2];
        FullBodyFocus = Attributes[3];
    }

    public Strength(Workout workout) {
        this.description = workout.description;
        this.difficulty = workout.difficulty;
        this.length = workout.length;
        this.sportFocus = workout.sportFocus;
        this.workoutType = workout.workoutType;
    }

    public void setBodyWeightFocus(boolean bodyWeightFocus) {
        BodyWeightFocus = bodyWeightFocus;
    }

    public void setFullBodyFocus(boolean fullBodyFocus) {
        FullBodyFocus = fullBodyFocus;
    }

    public void setLowerBodyFocus(boolean lowerBodyFocus) {
        LowerBodyFocus = lowerBodyFocus;
    }

    public void setUpperBodyFocus(boolean upperBodyFocus) {
        UpperBodyFocus = upperBodyFocus;
    }

    @Override
    public String toString() {
        return description + "\n\n" + "Length: " + this.length + ", Difficulty: " + this.difficulty + ", Sport Focus: " + this.sportFocus + "\nUpperBody Focus: " + UpperBodyFocus
                + "\nLowerBody Focus: " + LowerBodyFocus + "\nFullBodyFocus: " + FullBodyFocus
                + "\nBodyWeight Focus: " + BodyWeightFocus;
    }

    public boolean isBodyWeightFocus() {
        return BodyWeightFocus;
    }

    public boolean isFullBodyFocus() {
        return FullBodyFocus;
    }

    public boolean isLowerBodyFocus() {
        return LowerBodyFocus;
    }

    public boolean isUpperBodyFocus() {
        return UpperBodyFocus;
    }
}