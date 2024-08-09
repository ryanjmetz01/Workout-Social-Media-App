package edu.vassar.cmpu203.workoutapp.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class StrengthTest {

    /**
     * Testing the toString for a strength workout
     */
    @Test
    void testToString() {
        //creating a strength workout
        boolean[] values = new boolean[4];
        values[0] = true;
        Workout strength = new Strength(values);
        strength.setDescription("FUN");
        strength.setLength(5);
        strength.setDifficulty(2);

        //testing that the create workout matches what is expected
        assertEquals("FUN\n\nLength: 5, Difficulty: 2, Sport Focus: None\nUpperBody Focus: true\nLowerBody Focus: false\nFullBodyFocus: false" +
                "\nBodyWeight Focus: false", strength.toString());
    }
}