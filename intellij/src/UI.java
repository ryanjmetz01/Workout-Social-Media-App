import java.util.Scanner;
public class UI {

    Scanner scan = new Scanner(System.in);

    UI() {}

     void intro() {
        System.out.println("Welcome to the ultimate Social Workout App!!");

    }

    String action() {
        System.out.println("Do you want to view or post workouts? (view or post)" +
                "\n\t (quit) to exit");

        while(true) {
            String action = scan.next();
            if (action.equalsIgnoreCase("view") || action.equalsIgnoreCase("post") || action.equalsIgnoreCase("quit")) {
                return action;
            }
            else {
                System.out.println("Enter a valid action (post, view, quit)");
            }
        }


    }

    void outro() {
        System.out.println("Bye!");
    }

    void successfulPost() {
        System.out.println("Post successfully added\n");
    }

    void showFeed(Feed f) {
        System.out.println(f);
    }

    String askWorkoutType() {
        System.out.println("Please enter what type of workout you want to add (Cardio or Strength)");

        while (true) {
            String type =  scan.next();
            if (type.equalsIgnoreCase("cardio") || type.equalsIgnoreCase("Strength"))
                return type;
            else {
                System.out.println("Please enter a valid type of workout(Cardio or Strength)");
            }
        }
    }

    void workoutLimitWarning() {
        System.out.println("This post already has a workout added");
    }

    void captionLimitWarning() {
        System.out.println("This post already has a caption");
    }

    void successfulWorkout(){
        System.out.println("Workout successfully added");
    }

    String postOptions() {
        System.out.println("Do you want to add a workout (workout) or add a caption (caption) to your post? \n\t Enter \"done\" when post is ready");
        while (true) {
            String option = scan.next();
            if (option.equalsIgnoreCase("workout") || option.equalsIgnoreCase("caption") || option.equalsIgnoreCase("done")) {
                return option;
            }
            else {
                System.out.println("Pick a valid option for the post (workout, caption, done)");
            }
        }

    }

    void emptyFeed() {
        System.out.println("There are no posts");
    }

    String askCaption() {
        System.out.println("Please enter your caption for the post");
        scan.nextLine();
        return scan.nextLine();
    }

    void successfulCaption() {
        System.out.println("Caption successfully added");
    }

    String workoutDescription() {
        System.out.println("Please Enter a description for the workout");
        scan.nextLine();
        return scan.nextLine();
    }

    int getWorkoutLength() {
        System.out.println("How long is your workout? (Enter as a number in the format: 000 " +
                "with the first digit being hours and the last two digits minutes)");
        while (true) {
            try {
                int l = Integer.parseInt(scan.next());
                if (l > 999 || l < 1) {
                    System.out.println("Enter a valid length");
                    continue;
                }
                return l;
            }
            catch(NumberFormatException e) {
                System.out.println("Please enter a valid length");
            }

        }
    }

    int getWorkoutDifficulty() {
        System.out.println("How do you rate the difficulty of this workout? (A number from 1 to 5)");
        while(true) {
            try {
                int d = Integer.parseInt(scan.next());
                if (d > 5 || d < 1){
                    System.out.println("Enter a valid difficulty (A number between 1 and 5)");
                    continue;
                }
                return d;
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid difficulty");
            }
        }
        }

    String askSpecificAttributeCardio() {
        System.out.println("Which focus do you want to set? (Endurance, Agility, Speed) \n Type \"done\" when done setting attributes");


        while (true) {
            String atr = scan.next();
            if (atr.equalsIgnoreCase("endurance") || atr.equalsIgnoreCase("agility") || atr.equalsIgnoreCase("speed") || atr.equalsIgnoreCase("done")) {
                return atr;
            } else {
                atrWarning();
            }
        }
    }

    String askSpecificAttributeStrength() {
        System.out.println("Which focus do you want to set? (Upper, Lower, Full, Body) \n Type \"done\" when done setting attributes");

        while (true) {
            String atr = scan.next();
            if (atr.equalsIgnoreCase("upper") || atr.equalsIgnoreCase("lower") || atr.equalsIgnoreCase("full") || atr.equalsIgnoreCase("body") || atr.equalsIgnoreCase("done")) {
                return atr;
            } else {
                atrWarning();
            }
        }
    }

    void atrWarning() {
        System.out.println("Pick a valid type of attribute");
    }


}
