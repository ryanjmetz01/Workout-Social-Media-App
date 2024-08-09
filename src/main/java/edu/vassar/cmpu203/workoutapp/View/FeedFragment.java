package edu.vassar.cmpu203.workoutapp.View;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import edu.vassar.cmpu203.workoutapp.Controller.MainActivity;
import edu.vassar.cmpu203.workoutapp.Model.Feed;
import edu.vassar.cmpu203.workoutapp.Model.Post;
import edu.vassar.cmpu203.workoutapp.databinding.FragmentFeedBinding;

public class FeedFragment extends Fragment implements IFeedView {

    private FragmentFeedBinding binding;
    private IFeedView.Listener listener;
    private Feed feed;

    public FeedFragment(Listener listener, Feed feed) {
       this.listener = listener;
       this.feed = feed;
    }

    public FeedFragment(Listener listener){ this.listener = listener; }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       this.binding = FragmentFeedBinding.inflate(inflater);
       return this.binding.getRoot();
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState ) {

        feed = this.listener.getCurFeed();

        onFeedUpdated(this.feed);

        /**
         * sets the click for the add post button
         */
        this.binding.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FeedFragment.this.listener.onAddPost();
            }
        });

        /**
         * sets the click for the filter button
         */
        this.binding.FeedFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FeedFragment.this.listener.onFilter();
            }
        });

        /**
         * sets the click for the remove filters button
         */
        this.binding.RemoveFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FeedFragment.this.listener.removeFilters();
            }
        });

        /**
         * sets the click for the view profile button
         */
        this.binding.ViewProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FeedFragment.this.listener.viewProfile();
            }
        });
    }

    /**
     * when the feed is updated, print out each post that is present in the feed
     * dynamically create the views that will be used to display the feed
     * @param feed the feed be printed out
     */
    @Override
    public void onFeedUpdated(Feed feed) {
        LinearLayout linearLayout = this.binding.feedLayout;

        for(Post post : feed.feed) {
            Button b = new Button(getContext());
            b.setBackgroundColor(Color.BLUE);
            b.setText(post.getProd_id());
            b.setTextColor(Color.WHITE);
            b.setId(View.generateViewId());
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // match the text of button to prod_id of a user
                    String prof_ID =  (String) b.getText();
                    FeedFragment.this.listener.onProfileClick(prof_ID);
                    // display new screen of their profile
                }
            });
            linearLayout.addView(b);
            TextView tv = new TextView(getContext());
            tv.setText(post.toString());
            tv.setTextColor(Color.BLACK);
            linearLayout.addView(tv);
        }
    }
}