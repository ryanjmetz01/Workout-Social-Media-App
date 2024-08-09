package edu.vassar.cmpu203.workoutapp.Model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Feed implements Serializable {
    public List<Post> feed;

    public Feed() {
        feed = new ArrayList<>();
    }

    public String toString() {
        String res = "";

        for (Post post : feed) {
            res += post.toString() + "\n\n";
        }

        return res;
    }

    /**
     * adds a post to the feed
     * @param post the post to be added
     */
    public void addPosts(Post post) {
        this.feed.add(post);
    }

    public List<Post> getFeed() {
        return feed;
    }
}
