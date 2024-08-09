package edu.vassar.cmpu203.workoutapp.Model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PostTest {

    /**
     * Testing that the toString works for a Post
     */
    @Test
    void testToString() {
        //creating a post to test against
        Profile profile = new Profile();
        profile.setUsername("CoolDude");
        Post post = new Post(profile);
        post.addCaption("Nice workout");
        Workout workout = new Workout();
        workout.setDescription("FUN");
        workout.setLength(5);
        workout.setDifficulty(2);
        post.setWorkout(workout);


        //seeing that the created post matches what is expected
        assertEquals("\nWorkout: FUN\n5\n2\n\nNice workout",post.toString());
    }
}