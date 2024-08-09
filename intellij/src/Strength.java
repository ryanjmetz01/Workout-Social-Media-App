
public class Strength extends Workout {
    private boolean UpperBodyFocus;
    private boolean LowerBodyFocus;
    private boolean FullBodyFocus;
    private boolean BodyWeightFocus;

    Strength() {}

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

    public String toString() {
        return workout + "\n\n" + "Length: " + this.length + ", Difficulty: " + this.difficulty + "\n UpperBody Focus: " + UpperBodyFocus
                + "\n LowerBody Focus: " + LowerBodyFocus + "\n FullBodyFocus: " + FullBodyFocus
                + "\n BodyWeight Focus: " + BodyWeightFocus;
    }
}
