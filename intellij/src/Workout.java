public abstract class Workout {
    protected int length;
    protected int difficulty;
    protected String workout;
    protected String sportFocus;

    public void createWorkout(String description) {
        this.workout = description;
    }

    //public void setSport(String sport) { this.sportFocus = sport;}

    public void setLength(int length) {
        this.length = length;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

   // public boolean setAttribute(String name) {
     //  boolean attribute = false;
       // String a = scan.next();
        //if (a.equalsIgnoreCase("yes"))
          //  attribute = true;
        //return attribute;
    //}

   // abstract void setSpecificAttributes();

    abstract public String toString();
}
