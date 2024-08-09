package edu.vassar.cmpu203.workoutapp.Model;

import java.io.Serializable;

public class Post implements Serializable {

    private String prod_id;
    private String caption;
    private int WRKnum;
    private int CAPnum;
    public Workout workout;
    private String id;

    public Post(){}

    public Post(Profile profile) {
        this.prod_id = profile.getUsername();
        WRKnum = 0;
        CAPnum = 0;
    }

    public void setWorkout(Workout workout){
        this.workout = workout;
        this.WRKnum = 1;
    }

    public void addCaption(String caption) {
        this.caption = caption;
        this.CAPnum = 1;
    }

    void addWorkoutDescription(Workout w, String description) {
        w.setDescription(description);
    }

    void addWorkoutLength(Workout w, int length) {
        w.setLength(length);
    }

    void addWorkoutDifficulty(Workout w, int difficulty) {
        w.setDifficulty(difficulty);
    }



    public String toString() {

        return "\nWorkout: " + workout + "\n\n" + caption;
    }

    public int getCAPnum() {
        return CAPnum;
    }

    public int getWRKnum() {
        return WRKnum;
    }

    public String getProd_id() {
        return prod_id;
    }

    public String getCaption() {
        return caption;
    }

    public Workout getWorkout() {
          return workout;
    }

    public void setProd_id(String prod_id) {
        this.prod_id = prod_id;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
