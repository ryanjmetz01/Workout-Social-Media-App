package edu.vassar.cmpu203.workoutapp.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class WorkoutTest {

    /**
     * Testing that the toString works for a workout
     */
    @Test
    void testToString() {
        //creating a workout to test
        Workout workout = new Workout();
        workout.setDescription("FUN");
        workout.setLength(5);
        workout.setDifficulty(2);

        //checking that the created workout matches what is expected
        assertEquals("FUN\n5\n2", workout.toString());

    }
}