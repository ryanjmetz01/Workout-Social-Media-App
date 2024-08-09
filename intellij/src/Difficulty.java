import java.util.ArrayList;
import java.util.Scanner;

public class Difficulty implements Filter {
    int difficulty;
    ArrayList<Post> filtered = new ArrayList();

    public Difficulty(int difficulty, Feed f) {
        this.difficulty = difficulty;
        filtered = f.filteredFeed;
    }

    public ArrayList<Post> filter() {
        filtered.removeIf(post -> post.workout.difficulty != difficulty && difficulty != 0);
        return filtered;
    }
}
