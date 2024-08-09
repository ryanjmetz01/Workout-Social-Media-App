import java.util.ArrayList;

public interface Filter {
    public ArrayList<Post> filter();

    //Make constructor for each subclass that sets attributes

//    public static void filter(Feed f) {
//        Scanner scan = new Scanner(System.in);
//        System.out.println("Would you like to apply any filters to your feed? (yes or no)");
//        String s = scan.next();
//        if (s.equalsIgnoreCase("yes")) {
//            Scanner scan1 = new Scanner(System.in);
//            System.out.println("What is your preferred difficulty (1 to 5, 0 if no preference)");
//            int d = scan.nextInt();
//            Scanner scan2 = new Scanner(System.in);
//            System.out.println("What is your preferred length for the workout (000 (Hour and minutes), 000 if no preference");
//            int l = scan.nextInt();
//            f.filter(l, d);
//        }
//        else
//            f.filteredFeed = f.feed;
//        return;
//
//
//    }



}
