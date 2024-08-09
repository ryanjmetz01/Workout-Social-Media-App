import java.util.Scanner;

public class Controller {

    Controller() {}

    public static void main(String[] args) {
        Feed f = new Feed();
        UI ui = new UI();
        ui.intro();


            while (true) {
                String choice = ui.action();
                if (choice.equalsIgnoreCase("post")) {
                    addPost(f, ui);
                } else if (choice.equalsIgnoreCase("view")) {
                    displayPosts(f, ui);
                } else {
                    ui.outro();
                    break;
                }
            }

    }

    static void addPost(Feed f, UI ui) {
        Post p = new Post();

        while (true) {
            String action = ui.postOptions();

            if (action.equalsIgnoreCase("workout")) {

                if (p.getWRKnum() == 1) {
                    ui.workoutLimitWarning();
                    continue;
                }

                String type = ui.askWorkoutType();

                Workout w = p.addWorkout(type);

                String description = ui.workoutDescription();
                p.addWorkoutDescription(w, description);

                int length = ui.getWorkoutLength();
                p.addWorkoutLength(w, length);

                int difficulty = ui.getWorkoutDifficulty();
                p.addWorkoutDifficulty(w, difficulty);

                addSpecificAttributes(w, ui);

                ui.successfulWorkout();


                continue;

            } else if (action.equalsIgnoreCase("caption")) {
                if (p.getCAPnum() == 1) {
                    ui.captionLimitWarning();
                    continue;
                }

                String caption = ui.askCaption();
                p.addCaption(caption);
                ui.successfulCaption();
                continue;

            } else  {
                f.feed.add(p);
                ui.successfulPost();
                break;

            }
        }
    }

    static void displayPosts(Feed f, UI ui) {

        if(f.feed.size() == 0){
            ui.emptyFeed();
        }

//        f.filteredFeed = f.feed;
//        Scanner scan1 = new Scanner(System.in);
//        System.out.println("What is your preferred difficulty (1 to 5, 0 if no preference)");
//        int d = scan1.nextInt();
//        Difficulty dif = new Difficulty(d, f);
//        f.filteredFeed = dif.filter();
//        Scanner scan2 = new Scanner(System.in);
//        System.out.println("What is your preferred length for the workout (000 (Hour and minutes), 000 if no preference)");
//        int l = scan2.nextInt();
//        Length len = new Length(l, f);
//        f.filteredFeed = dif.filter();
        ui.showFeed(f);
    }

    static void addSpecificAttributes(Workout w, UI ui) {
        if (w instanceof Cardio) {
            while (true) {
                String cardioATR = ui.askSpecificAttributeCardio();
                if (cardioATR.equalsIgnoreCase("endurance")) {
                    ((Cardio) w).setEnduranceFocus(true);
                } else if (cardioATR.equalsIgnoreCase("agility")) {
                    ((Cardio) w).setAgilityFocus(true);
                } else if (cardioATR.equalsIgnoreCase("speed")) {
                    ((Cardio) w).setSpeedFocus(true);
                }
                else  {
                    break;
                }
            }
        }
        else {
            while (true) {
                String strengthATR = ui.askSpecificAttributeStrength();
                if (strengthATR.equalsIgnoreCase("Upper")) {
                    ((Strength) w).setUpperBodyFocus(true);
                } else if (strengthATR.equalsIgnoreCase("lower")) {
                    ((Strength) w).setLowerBodyFocus(true);
                } else if (strengthATR.equalsIgnoreCase("full")) {
                    ((Strength) w).setBodyWeightFocus(true);
                }
                else if (strengthATR.equalsIgnoreCase("body")) {
                    ((Strength) w).setBodyWeightFocus(true);
                }
                else {
                    break;
                }
            }
        }

    }
    }




