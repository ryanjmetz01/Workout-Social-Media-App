
public class Cardio extends Workout {
    private boolean EnduranceFocus = false;
    private boolean AgilityFocus = false;
    private boolean SpeedFocus = false;

    public Cardio() {}

    public void setAgilityFocus(boolean agilityFocus) {
        AgilityFocus = agilityFocus;
    }

    public void setEnduranceFocus(boolean enduranceFocus) {
        EnduranceFocus = enduranceFocus;
    }

    public void setSpeedFocus(boolean speedFocus) {
        SpeedFocus = speedFocus;
    }

    public String toString() {
        return workout + "\n\n" + "Length: " + this.length + ", Difficulty: " + this.difficulty + "\nEndurance Focus: " + EnduranceFocus
                + "\nAgility Focus: " + AgilityFocus + "\nSpeed Focus: " + SpeedFocus;
    }

}
