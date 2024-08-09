package edu.vassar.cmpu203.workoutapp.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MobilityTest {

    /**
     * Tests that the toString methods works for a Cardio workout
     */
    @Test
    void testToString() {
        //creates a cardio workout to test against
        boolean[] values = new boolean[3];
        values[0] = true;
        Workout mobility = new Mobility(values);
        mobility.setDescription("FUN");
        mobility.setLength(5);
        mobility.setDifficulty(2);

        //makes sure that the created cardio workout matches the expected string
        assertEquals("FUN\n\nLength: 5, Difficulty: 2, Sport Focus: None\nStatic Stretching: true\nDynamic Stretching: false\nYoga: false", mobility.toString());

    }
}
