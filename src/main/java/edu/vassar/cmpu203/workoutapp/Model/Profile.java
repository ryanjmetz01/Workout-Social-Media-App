package edu.vassar.cmpu203.workoutapp.Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Profile implements Serializable {
    private String username;
    private AuthKey password;
    public Feed posts;
    private String bio;
    private int numFollowers;
    private int numFollowing;
    private int numPosts;
    private Map<String, Profile> followRequests;
    private Map<String, Profile> followers;

    public Profile() {
        this.posts = new Feed();
        this.followRequests = new LinkedHashMap<>();
        this.followers = new LinkedHashMap<>();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPasswordFromString(String password) {
        this.password = new AuthKey(password);
    }
    public void setPassword(AuthKey password) {
        this.password = password;
    }


    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setNumPosts(){
        numPosts++;
    }

    public void setNumFollowers(){numFollowers++; }
    public void setNumFollowing(){numFollowing++; }

    public String getUsername() { return this.username; }
    public AuthKey getPassword() { return this.password;}
    public Feed getPosts(){return this.posts;}
    public String getBio(){return this.bio;}
    public int getNumFollowers() { return numFollowers; }
    public int getNumFollowing() { return numFollowing; }
    public int getNumPosts(){ return this.numPosts; }
    public Map<String, Profile> getFollowRequests(){return this.followRequests;}
    public Map<String, Profile> getFollowers(){return this.followers;}

    /**
     * validates that the password is correct
     * @param password the password to be validated
     * @return a boolean representing if the password was validated or not
     */
    public boolean validatePassword(String password){
        return this.password.validatePassword(password);
    }
}