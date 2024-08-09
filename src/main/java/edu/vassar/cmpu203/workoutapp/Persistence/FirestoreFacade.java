package edu.vassar.cmpu203.workoutapp.Persistence;

import androidx.annotation.NonNull;

import edu.vassar.cmpu203.workoutapp.Model.Cardio;
import edu.vassar.cmpu203.workoutapp.Model.Feed;
import edu.vassar.cmpu203.workoutapp.Model.Mobility;
import edu.vassar.cmpu203.workoutapp.Model.Post;
import edu.vassar.cmpu203.workoutapp.Model.Profile;
import edu.vassar.cmpu203.workoutapp.Model.Strength;
import edu.vassar.cmpu203.workoutapp.Model.Workout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;


public class FirestoreFacade implements IPersistenceFacade {

    private static final String POST_COLLECTION = "POST_COLLECTION";
    private static final String PROFILE_COLLECTION = "PROFILE_COLLECTION";

    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public FirestoreFacade(){}

    /**
     * retrieve the entire post collection from the interface and stores the workouts of each post as the
     * correct workout type
     * @param listener interface to determine what to do when data is found
     */
    @Override
    public void retrieveFeed(DataListener<Feed> listener) {
        db.collection(POST_COLLECTION).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot qsnap) {
                        Feed feed = new Feed();
                        for(DocumentSnapshot dsnap : qsnap){

                            Post post = dsnap.toObject(Post.class);

                            if(post.getWorkout().getWorkoutType() == 1) {
                              Cardio cardio = new Cardio(post.getWorkout());
                              cardio.setAgilityFocus(dsnap.getBoolean("workout.agilityFocus"));
                              cardio.setEnduranceFocus(dsnap.getBoolean("workout.enduranceFocus"));
                              cardio.setSpeedFocus(dsnap.getBoolean("workout.speedFocus"));
                              post.setWorkout(cardio);
                            }
                            else if (post.getWorkout().getWorkoutType() == 2 ){
                                Strength strength = new Strength(post.getWorkout());
                                strength.setBodyWeightFocus(dsnap.getBoolean("workout.bodyWeightFocus"));
                                strength.setFullBodyFocus(dsnap.getBoolean("workout.fullBodyFocus"));
                                strength.setUpperBodyFocus(dsnap.getBoolean("workout.upperBodyFocus"));
                                strength.setLowerBodyFocus(dsnap.getBoolean("workout.lowerBodyFocus"));
                                post.setWorkout(strength);
                            }
                            else if (post.getWorkout().getWorkoutType() == 3) {
                                Mobility mobility = new Mobility(post.getWorkout());
                                mobility.setDynamicFocus(dsnap.getBoolean("workout.dynamicStretching"));
                                mobility.setStaticFocus(dsnap.getBoolean("workout.staticStretching"));
                                mobility.setYogaFocus(dsnap.getBoolean("workout.yoga"));
                                post.setWorkout(mobility);
                            }


                            feed.addPosts(post);
                        }
                        listener.onDataReceived(feed);
                    }
                });
    }

    /**
     * adds a profile to the database if it does not already exist
     * @param profile the profile ot be added
     * @param listener interface to determine what happens if data is found
     */
    @Override
    public void addProfile(@NonNull Profile profile, @NonNull BinaryResultListener listener) {
        String username = profile.getUsername();

        this.retrieveProfile(username, new DataListener<Profile>() {
            @Override
            public void onDataReceived(@NonNull Profile data) {
                listener.onNoResult();
            }

            @Override
            public void onNoDataFound() {
                db.collection(PROFILE_COLLECTION).document(username).
                        set(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        listener.onYesResult();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onNoResult();
                    }
                });
            }
        });
    }

    /**
     * saves a post to the data base and returns the id that it is stored under
     * @param post the post to be saved
     * @return a string representing the id that the post is saved under
     */
    @Override
    public String savePost(Post post) {
        CollectionReference colref = db.collection(POST_COLLECTION);
        DocumentReference docref = colref.document();
        String docId = docref.getId();


        db.collection(POST_COLLECTION).document(docId).set(post);

        return docId;
    }

    /**
     * edit a post in the database
     * @param post the post ot be edited
     * @param id the id that the post is saved under
     */
    @Override
    public void editPost(Post post, String id){
        db.collection(POST_COLLECTION).document(id).set(post);
    }

    /**
     * retrieves a profile from the database
     * @param username the username of the profile
     * @param listener interface for when the data is received or not
     */
    @Override
    public void retrieveProfile(@NonNull String username, @NonNull DataListener<Profile> listener) {

        db.collection(PROFILE_COLLECTION).document(username).get().
                addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        if (documentSnapshot.exists()){
                            Profile profile = documentSnapshot.toObject(Profile.class);
                            listener.onDataReceived(profile);
                        }
                        else {
                            listener.onNoDataFound();
                        }
                    }
                });
    }


    /**
     * saves a profile to the data base
     * @param p the profile to be saved
     */
    @Override
    public void saveProfile(Profile p) {
        db.collection(PROFILE_COLLECTION).document(p.getUsername()).set(p);
    }


    //These methods will help with rerunning tests

    /**
     * removes all the posts from database
     */
  @Override
    public void removePosts() {

        db.collection(POST_COLLECTION).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot qSnap) {
                for(DocumentSnapshot dsnap : qSnap){
                    dsnap.getReference().delete();
                }
            }
        });
  }

    /**
     * removes a single profile from the database
     * @param profile the profile to be removed
     */
    @Override
    public void removeUser(Profile profile) {
        db.collection(PROFILE_COLLECTION).document(profile.getUsername()).delete();
    }



}
