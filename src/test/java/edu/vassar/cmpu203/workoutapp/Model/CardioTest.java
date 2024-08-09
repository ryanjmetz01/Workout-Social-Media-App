package edu.vassar.cmpu203.workoutapp.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CardioTest {

    /**
     * Tests that the toString methods works for a Cardio workout
     */
    @Test
    void testToString() {
        //creates a cardio workout to test against
        boolean[] values = new boolean[3];
        values[0] = true;
        Workout cardio = new Cardio(values);
        cardio.setDescription("FUN");
        cardio.setLength(5);
        cardio.setDifficulty(2);

        //makes sure that the created cardio workout matches the expected string
        assertEquals("FUN\n\nLength: 5, Difficulty: 2, Sport Focus: None\nEndurance Focus: false\nAgility Focus: true\nSpeed Focus: false", cardio.toString());


    }
}