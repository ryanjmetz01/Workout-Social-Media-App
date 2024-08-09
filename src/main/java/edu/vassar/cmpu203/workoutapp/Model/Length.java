package edu.vassar.cmpu203.workoutapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Length implements Filter {
    int length;
    List<Post> filtered = new ArrayList();

    public Length(int length, Feed f) {
        this.length = length;
        filtered = f.feed;
    }

    /**
     * filters the list based on the length
     * @return the new filtered list
     */
    public List<Post> filter() {
        filtered.removeIf(post -> (post.workout == null) || (post.workout.length != length && length != 0));
        return filtered;

    }
}
