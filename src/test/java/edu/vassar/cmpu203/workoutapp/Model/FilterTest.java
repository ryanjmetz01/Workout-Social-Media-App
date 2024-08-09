package edu.vassar.cmpu203.workoutapp.Model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class FilterTest {

    /**
     * Adds three posts and then uses filter() to filter through the posts and check if the remaining post is the expected one
     */
    @Test
    void testFilteredFeed() {
        Feed f = new Feed();

        //Add workout to feed
        Profile profile = new Profile();
        profile.setUsername("CoolDude");
        Post post = new Post(profile);
        post.addCaption("Nice workout");
        Workout workout = new Workout();
        workout.setDescription("FUN");
        workout.setLength(5);
        workout.setDifficulty(2);
        workout.setSport("Lacrosse");
        post.setWorkout(workout);

        f.feed.add(post);

        //Add second workout to feed
        Profile profile1 = new Profile();
        profile1.setUsername("CoolDude");
        Post post1 = new Post(profile1);
        post1.addCaption("Nice workout");
        Workout workout1 = new Workout();
        workout1.setDescription("FUN");
        workout1.setLength(3);
        workout1.setDifficulty(2);
        workout1.setSport("None");
        post1.setWorkout(workout1);

        f.feed.add(post1);

        //Add third workout to feed
        Profile profile2 = new Profile();
        profile2.setUsername("TestMan");
        Post post2 = new Post(profile2);
        post2.addCaption("fire workout");
        Workout workout2 = new Cardio();
        workout2.setDescription("Intense");
        workout2.setLength(3);
        workout2.setDifficulty(1);
        workout2.setSport("Tennis");
        workout2.setType(1);
        post2.setWorkout(workout2);

        f.feed.add(post2);

        //Create Filters for feed
        Filter l = new Length(3, f);
        f.feed = l.filter();
        Filter d = new Difficulty(1, f);
        f.feed = d.filter();
        Filter t = new Type(1, f);
        f.feed = t.filter();
        Filter s = new Sport("Tennis", f);
        f.feed = s.filter();



        assertEquals("\nWorkout: Intense\n\nLength: 3, Difficulty: 1, Sport Focus: Tennis\nEndurance Focus: false\nAgility Focus: false\nSpeed Focus: false\n\nfire workout\n\n", f.toString());
    }
}
