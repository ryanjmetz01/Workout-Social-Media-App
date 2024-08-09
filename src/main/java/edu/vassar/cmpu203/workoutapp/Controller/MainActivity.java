package edu.vassar.cmpu203.workoutapp.Controller;

import edu.vassar.cmpu203.workoutapp.Model.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.google.firebase.firestore.DocumentReference;

import edu.vassar.cmpu203.workoutapp.Persistence.FirestoreFacade;
import edu.vassar.cmpu203.workoutapp.Persistence.IPersistenceFacade;
import edu.vassar.cmpu203.workoutapp.View.*;

import java.util.ArrayList;
import java.util.List;

/**
 * the controller for our program
 */
public class MainActivity extends AppCompatActivity implements ICreatePostView.Listener, IAddWorkout.Listener, ICreateProfileView.Listener, IFeedView.Listener, IWorkoutType.Listener, IFilterView.Listener, IHomeScreenView.Listener, IViewProfileView.Listener, IEditProfileView.Listener, IViewOtherProfileView.Listener, IFollowRequestView.Listener {

    private IMainView mainView;
    private Feed curFeed;
    private Feed unFeed = new Feed();
    private Feed filteredFeed = new Feed();
    private Profile curUser;
    private Post curPost;
    private Workout curWorkout;
    // another profile to help view other profiles
    private Profile user2;

    private IPersistenceFacade persistenceFacade = new FirestoreFacade();


    /**
     * when this activity is created
     * @param savedInstanceState either null of storing the fields of the class when activity destoryed
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportFragmentManager().setFragmentFactory(new WorkoutAppFragFactory(this));

        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            this.curFeed = new Feed();
        }
        else {
            this.curFeed = (Feed) savedInstanceState.getSerializable("FEED");
            this.curUser = (Profile) savedInstanceState.getSerializable("CUR_USER");
            this.curPost = (Post) savedInstanceState.getSerializable("CUR_POST");
            this.curWorkout = (Workout) savedInstanceState.getSerializable("CUR_WORKOUT");
        }

        this.persistenceFacade.retrieveFeed(new IPersistenceFacade.DataListener<Feed>() {
            @Override
            public void onDataReceived(@NonNull Feed feed) {
                MainActivity.this.unFeed = feed;
                MainActivity.this.curFeed = unFeed;
                Fragment curFrag = MainActivity.this.mainView.getCurrentFragment();
                if(curFrag instanceof IFeedView)
                    ((IFeedView) curFrag).onFeedUpdated(curFeed);
            }


            @Override
            public void onNoDataFound() {

            }
        });

        this.mainView = new MainView(this);
        setContentView(this.mainView.getRootView());

        if(savedInstanceState == null || !savedInstanceState.getBoolean("IN_PROGRESS"))
        this.mainView.displayFragment(HomeScreenFragment.class, null, false);
    }

    /**
     * called when a caption is added to a post
     * @param caption the caption to be added
     * @param createPostView the view that is active at the time
     */
    @Override
    public void onAddedCaption(String caption, ICreatePostView createPostView) {
        this.curPost.addCaption(caption);
        createPostView.updateCaption(caption);
    }

    /**
     * called when a workout is added to a post
     * @param length  the length of the workout
     * @param difficulty the difficulty of the workout
     * @param descr the description of the workout
     * @param sport the sport focus of the workout
     */
    @Override
    public void onAddedWorkout(int length, int difficulty, String descr, String sport) {
        this.curWorkout.setDescription(descr);
        this.curWorkout.setLength(length);
        this.curWorkout.setDifficulty(difficulty);
        this.curWorkout.setSport(sport);
        this.curPost.setWorkout(this.curWorkout);
        this.mainView.displayFragment(Create_Post_Fragment.class, null, false);
    }

    /**
     * called when the user clicks the add workout button to add a workout to the post
     */
    @Override
    public void onWorkoutButton() {

        this.mainView.displayFragment(AddWorkoutFragment.class, null, false);
    }

    /**
     * When the user clicks on the post button, adds the post to the feed
     */
    @Override
    public void onPostButton() {
        this.unFeed.feed.add(this.curPost);
        this.curUser.posts.addPosts(this.curPost);
        this.curUser.setNumPosts();
        String id = this.persistenceFacade.savePost(this.curPost);
        updatePostId(id);
        this.persistenceFacade.saveProfile(this.curUser);
        this.mainView.displayFragment(FeedFragment.class, null, false);
    }

    /**
     * private method that sets the field ID in the post so that we can access the random
     * id of the post document
     * @param id random id that firestore generated
     */
    private void updatePostId(String id){
        this.curPost.setId(id);
        this.persistenceFacade.editPost(this.curPost, id);
    }

    /**
     * called when user clicks on the cancel button on the addPost screen
     */
    @Override
    public void onCancelButton() {
        this.mainView.displayFragment(FeedFragment.class, null, false);
    }

    /**
     * When the user clicks the set to change their password
     * @param password the new password
     * @param editProfileView the current view
     */
    @Override
    public void onEditedPassword(String password, IEditProfileView editProfileView) {
        curUser.setPasswordFromString(password);
        this.persistenceFacade.saveProfile(this.curUser);
    }

    /**
     * When the user clicks the set button to change their bio
     * @param bio The new bio
     * @param editProfileView the current view
     */
    @Override
    public void onEditedBio(String bio, IEditProfileView editProfileView) {

        curUser.setBio(bio);
        this.persistenceFacade.saveProfile(this.curUser);
    }

    /**
     * called when someone is creating a new profile
     * @param username the profile username
     * @param password the profile password
     * @param bio the profile bio
     * @param createProfileView the current view
     */
    @Override
    public void onCreateButton(String username, String password, String bio, ICreateProfileView createProfileView) {
        Profile p = new Profile();
        p.setUsername(username);
        p.setPasswordFromString(password);
        p.setBio(bio);
        this.persistenceFacade.addProfile(p, new IPersistenceFacade.BinaryResultListener() {
            @Override
            public void onYesResult() {
                MainActivity.this.curUser = p;
                createProfileView.onCreateSuccess();
                MainActivity.this.mainView.displayFragment(FeedFragment.class, null, false);
            }

            @Override
            public void onNoResult() {
                createProfileView.onUserAlreadyExists();
            }
        });
    }

    /**
     * called when user clicks on the add post button from the feed
     */
    @Override
    public void onAddPost() {
        Post post = new Post(this.curUser);
        Workout workout = new Workout();
        curPost = post;
        curWorkout = workout;
        this.mainView.displayFragment(Create_Post_Fragment.class, null, false);
    }

    /**
     * when the user clicks on the cardio button from the addworkout Screen to signify
     * that they want a workout of type cardio
     * @param length length of the workout
     * @param difficulty difficulty of the workout
     * @param descr description of the workout
     * @param sport sport focus of the workout
     */
    @Override
    public void CardioButton(int length, int difficulty, String descr, String sport) {
        this.curWorkout.setDescription(descr);
        this.curWorkout.setLength(length);
        this.curWorkout.setDifficulty(difficulty);
        this.curWorkout.setSport(sport);
        this.mainView.displayFragment(CardioFragment.class, null, false);
    }
    /**
     * when the user clicks on the strength button from the addworkout Screen to signify
     * that they want a workout of type strength
     * @param length length of the workout
     * @param difficulty difficulty of the workout
     * @param descr description of the workout
     * @param sport sport focus of the workout
     */
    @Override
    public void StrengthButton(int length, int difficulty, String descr, String sport) {
        this.curWorkout.setDescription(descr);
        this.curWorkout.setLength(length);
        this.curWorkout.setDifficulty(difficulty);
        this.curWorkout.setSport(sport);
        this.mainView.displayFragment(StrengthFragment.class, null, false);
    }
    /**
     * when the user clicks on the mobility button from the addworkout Screen to signify
     * that they want a workout of type mobility
     * @param length length of the workout
     * @param difficulty difficulty of the workout
     * @param descr description of the workout
     * @param sport sport focus of the workout
     */
    @Override
    public void MobilityButton(int length, int difficulty, String descr, String sport) {
        this.curWorkout.setDescription(descr);
        this.curWorkout.setLength(length);
        this.curWorkout.setDifficulty(difficulty);
        this.curWorkout.setSport(sport);
        this.mainView.displayFragment(MobilityFragment.class, null, false);
    }

    /**
     * after the user clicks set on any of the workout type screen, sets the kind of workout
     * @param Attributes boolean array of values relating to the focus of the work
     * @param workoutType the workout type (1 for cardio, 2 for strength, 3 for mobility)
     */
    @Override
    public void onAddedAttributes(boolean[] Attributes, int workoutType) {
        Cardio c;
        Strength s;
        Mobility m;
        if (workoutType == 1) {
            c = new Cardio(Attributes);
            c.setLength(curWorkout.getLength());
            c.setDifficulty(curWorkout.getDifficulty());
            c.setDescription(curWorkout.getDescription());
            c.setSport(curWorkout.getSportFocus());
            this.curWorkout = c;
        }
        else if (workoutType == 2) {
            s = new Strength(Attributes);
            s.setLength(curWorkout.getLength());
            s.setDifficulty(curWorkout.getDifficulty());
            s.setDescription(curWorkout.getDescription());
            s.setSport(curWorkout.getSportFocus());
            this.curWorkout = s;
        }

        else {
            m = new Mobility(Attributes);
            m.setLength(curWorkout.getLength());
            m.setDifficulty(curWorkout.getDifficulty());
            m.setDescription(curWorkout.getDescription());
            m.setSport(curWorkout.getSportFocus());
            this.curWorkout = m;
        }

        this.curWorkout.setType(workoutType);

        this.mainView.displayFragment(AddWorkoutFragment.class, null, false);
    }

    /**
     * takes the user to the filter screen
     */
    @Override
    public void onFilter() {
        this.mainView.displayFragment(FilterFragment.class, null, false);
    }

    /**
     * Sets the feed to be filtered based on the users input,
     * takes them to the feed
     * @param length user desired length
     * @param difficulty user desired difficulty
     * @param workoutType user desired workoutType
     * @param sport user desired sport focus
     */
    @Override
    public void onSetFilter(int length, int difficulty, int workoutType, String sport) {
        filteredFeed.feed = new ArrayList(this.curFeed.feed);
        Filter len = new Length(length, filteredFeed);
        filteredFeed.feed = len.filter();
        Filter diff = new Difficulty(difficulty, filteredFeed);
        filteredFeed.feed = diff.filter();
        Filter type = new Type(workoutType, filteredFeed);
        filteredFeed.feed = type.filter();
        Filter sprt = new Sport(sport, filteredFeed);
        filteredFeed.feed = sprt.filter();

        this.curFeed = filteredFeed;

        this.mainView.displayFragment(FeedFragment.class, null, false);
    }

    /**
     * removed the filters from the feed
     */
    @Override
    public void removeFilters() {
        this.curFeed = unFeed;
        this.mainView.displayFragment(FeedFragment.class, null, false);
    }

    /**
     * takes the user to the signup screen from the home page
     */
    @Override
    public void onSignUp(){
        this.mainView.displayFragment(CreateProfileFragment.class, null, false);
    }

    /**
     * attempts to login the user by mathcing thier inputs to a saved user on the database
     * @param username the users input for username
     * @param password the user input for password
     * @param homeScreenView the current view
     */
    @Override
    public void onLogIn(String username, String password, IHomeScreenView homeScreenView) {
        //p.setUsername(username);

        persistenceFacade.retrieveProfile(username, new IPersistenceFacade.DataListener<Profile>() {
            @Override
            public void onDataReceived(@NonNull Profile data) {
                if (data.validatePassword(password)) {
                    MainActivity.this.curUser = data;
                    homeScreenView.successfulLogIn();
                    MainActivity.this.mainView.displayFragment(FeedFragment.class, null, false );
                } else {
                    homeScreenView.onInvalidCredentials();
                }
            }

            @Override
            public void onNoDataFound() {
                homeScreenView.onInvalidCredentials();
            }
        });
    }

    /**
     * takes the user to the view profile screen from the feed
     */
    @Override
    public void viewProfile() {
        this.mainView.displayFragment(ViewProfileFragment.class, null, false);
    }

    /**
     * takes the user to the profile of another user based on the button that they click on the feed
     * will take the user to their own profile if they click on thier own name
     * @param prod_ID the id of the person who the user clicked on
     */
    @Override
    public void onProfileClick(String prod_ID) {
        persistenceFacade.retrieveProfile(prod_ID, new IPersistenceFacade.DataListener<Profile>() {
            @Override
            public void onDataReceived(@NonNull Profile data) {
                MainActivity.this.user2 = data;

                if(user2.getUsername().equals(MainActivity.this.curUser.getUsername()))
                    MainActivity.this.mainView.displayFragment(ViewProfileFragment.class, null, false);
                else
                    MainActivity.this.mainView.displayFragment(ViewOtherProfileFragment.class, null, false);
            }

            @Override
            public void onNoDataFound() {

            }
        });

    }

    /**
     * takes the user to the edit profile screen from the profile screen
     */
    @Override
    public void onEditProfile() {
        this.mainView.displayFragment(EditProfileFragment.class, null, false);
    }

    /**
     * will take the user back to the feed from the profile screen
     */
    @Override
    public void onGoBack() {
        this.mainView.displayFragment(FeedFragment.class, null, false);
    }

    /**
     * will take the user to the follow request screen so they can view their follow requests
     */
    @Override
    public void onFollowRequests() {
            this.mainView.displayFragment(FollowRequestFragment.class, null, false);
    }

    /**
     * when the user is done editing their profile, they are taken back to the feed
     */
    @Override
    public void onDoneButton(){
        this.mainView.displayFragment(FeedFragment.class, null, false);
    }

    /**
     * clicking the back button when viewing someone else's profile takes user
     * back to the feed
     */
    @Override
    public void goBack(){
        this.mainView.displayFragment(FeedFragment.class, null, false);
    }

    /**
     * checks to see if the curUser is already following, or has already requested this user
     * and then adds the current user to the profiles follow request
     * @param profile the profile that the curUser is viewing
     * @param iViewOtherProfileView the current view
     */
    @Override
    public void requestFollow(Profile profile, IViewOtherProfileView iViewOtherProfileView){
        // what if I already requested to follow this person
        if(profile.getFollowRequests().containsKey(this.curUser.getUsername()))
            iViewOtherProfileView.onAlreadyRequested();
        // what if I already follow this person
        else if(profile.getFollowers().containsKey(this.curUser.getUsername()))
            iViewOtherProfileView.onAlreadyFollowed();

        iViewOtherProfileView.onRequest();
        profile.getFollowRequests().put(this.curUser.getUsername(), curUser);
        this.persistenceFacade.saveProfile(profile);

    }

    /**
     * returns another profile for two person things
     * @return a profile
     */
    @Override
    public Profile getUser2() {
        return this.user2;
    }

    /**
     * saves the current state of the activity
     * @param outState the bundle that things are being placed into
     */
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putBoolean("IN_PROGRESS", true);
        outState.putSerializable("FEED", this.curFeed);
        outState.putSerializable("CUR_USER", curUser);
        outState.putSerializable("CUR_POST", this.curPost);
        outState.putSerializable("CUR_WORKOUT", this.curWorkout);
    }

    /**
     * takes the user back to their profile when they click go back when
     * viewing follow requests
     */
    @Override
    public void onBack() {
        this.mainView.displayFragment(ViewProfileFragment.class, null, false);
    }

    /**
     * when the user clicks accept on a follow request, updates their num of followers
     * and the profile's num of following, removes the follow request from the user
     * saves the users to the database
     * @param profile the profile who is requesting to follow
     */
    @Override
    public void onAccept(Profile profile) {
        this.curUser.setNumFollowers();
        profile.setNumFollowing();
        this.persistenceFacade.saveProfile(profile);
        this.curUser.getFollowRequests().remove(profile.getUsername(), profile);
        this.persistenceFacade.saveProfile(this.curUser);
    }

    /**
     * adds the new follower to the curUser followers
     * then saves the curUser to database
     * @param profile the new follower
     */
    @Override
    public void onAddedFollower(Profile profile){
        this.curUser.getFollowers().put(profile.getUsername(), profile);
        this.persistenceFacade.saveProfile(this.curUser);
    }

    /**
     * when the curUser declines the a follow request,
     * removes the follow request from the curUser follow requests
     * save curUser to database
     * @param profile the profile that is requesting to follow
     */
    @Override
    public void onDecline(Profile profile) {
        this.curUser.getFollowRequests().remove(profile.getUsername(), profile);
        this.persistenceFacade.saveProfile(this.curUser);
    }

    /**
     * returns the current feed
     * @return the current feed
     */
    @Override
    public Feed getCurFeed() {
        return curFeed;
    }

    /**
     * returns the current user
     * @return the current User
     */
    @Override
    public Profile getCurUser() {
        return curUser;
    }

    /**
     * returns the current workout
     * @return the current workout
     */
    @Override
    public Workout getCurWorkout() {
        return curWorkout;
    }

    /**
     * when the user clicks log out from the profile screen
     * @param viewProfileView the current view
     */
    @Override
    public void logout(IViewProfileView viewProfileView) {
        curUser = null;
        viewProfileView.onSuccessfulLogOut();
        this.mainView.displayFragment(HomeScreenFragment.class, null, false);
    }


}

