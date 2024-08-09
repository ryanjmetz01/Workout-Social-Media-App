package edu.vassar.cmpu203.workoutapp.Model;

import java.util.ArrayList;
import java.util.List;

public class Sport implements Filter {
    String sport;
    List<Post> filtered = new ArrayList();

    public Sport(String sport, Feed f) {
        this.sport = sport;
        filtered = f.feed;
    }

    /**
     * filters the list based on the sport focus
     * @return the new filtered list
     */
    public List<Post> filter() {
        filtered.removeIf(post -> (post.workout == null) || (!(post.workout.sportFocus.equals(sport)) && !(sport.equals("None"))));
        return filtered;
    }
}
