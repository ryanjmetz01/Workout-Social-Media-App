package edu.vassar.cmpu203.workoutapp.Model;

import java.io.Serializable;

public class Workout implements Serializable {
    public int length = 0;
    public int difficulty = 0;
    public String description = "";
    protected String sportFocus = "None";
    int workoutType;

    public Workout() {}

    public void setDescription(String description) {
        this.description = description;
    }

    public void setSport(String sport) { this.sportFocus = sport;}

    public void setLength(int length) {
        this.length = length;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public void setType(int type) { workoutType = type; }



    public String toString() {
        return this.description + "\n" + this.length + "\n" + this.difficulty + "\n" + this.sportFocus;
    }


    public int getDifficulty() {
        return difficulty;
    }
    public int getLength() { return this.length;}
    public String getDescription(){return this.description;}
    public String getSportFocus(){return this.sportFocus;}

    public int getWorkoutType() {
        return workoutType;
    }
}
