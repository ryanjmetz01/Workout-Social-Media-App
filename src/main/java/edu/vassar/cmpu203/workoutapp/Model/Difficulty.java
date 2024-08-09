package edu.vassar.cmpu203.workoutapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Difficulty implements Filter {
    int difficulty;
    List<Post> filtered = new ArrayList<Post>();

    public Difficulty(int difficulty, Feed f) {
        this.difficulty = difficulty;
        filtered = f.feed;
    }

    /**
     * filters the list based on the difficulty
     * @return the new filtered list
     */
    public List<Post> filter() {
        filtered.removeIf(post -> (post.workout == null) || (post.workout.difficulty != difficulty && difficulty != 0));
        return filtered;
    }
}
