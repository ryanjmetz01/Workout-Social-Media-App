public class Post {
    private String prod_id;
    private String caption;
    private int WRKnum;
    private int CAPnum;
    public Workout workout;

    public Post() {
        this.prod_id = "Prod_ID here";
        WRKnum = 0;
        CAPnum = 0;
    }

    public Workout addWorkout(String type) {
        Workout w;

            if (type.equalsIgnoreCase("cardio"))
                w = new Cardio();
            else
                w = new Strength();
        this.workout = w;
        this.WRKnum = 1;

        return w;
    }

    public void addCaption(String caption) {
        this.caption = caption;
        this.CAPnum = 1;
    }

    void addWorkoutDescription(Workout w, String description) {
        w.createWorkout(description);
    }

    void addWorkoutLength(Workout w, int length) {
        w.setLength(length);
    }

    void addWorkoutDifficulty(Workout w, int difficulty) {
        w.setDifficulty(difficulty);
    }

    // void addWorkoutSport(Workout w, String s) { w.setSport(s); }


    public String toString() {
        return "\n" + this.prod_id + "\nWorkout: " + workout + "\n\n" + caption;
    }

    public int getCAPnum() {
        return CAPnum;
    }

    public int getWRKnum() {
        return WRKnum;
    }
}
