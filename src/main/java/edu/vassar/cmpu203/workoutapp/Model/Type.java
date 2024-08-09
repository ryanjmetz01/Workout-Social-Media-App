package edu.vassar.cmpu203.workoutapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Type implements Filter{
    int type;
    List<Post> filtered = new ArrayList();

    public Type(int type, Feed f) {
        this.type = type;
        filtered = f.feed;
    }

    /**
     * filters the post based on the type
     * @return the new filter list
     */
    public List<Post> filter() {
        filtered.removeIf(post -> (post.workout == null) || (post.workout.workoutType != type && type != 0));
        return filtered;

    }
}
