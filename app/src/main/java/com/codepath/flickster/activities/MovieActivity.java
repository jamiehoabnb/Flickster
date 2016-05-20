package com.codepath.flickster.activities;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.codepath.flickster.R;
import com.codepath.flickster.fragments.NowPlayingFragment;
import com.codepath.flickster.fragments.PopularFragment;
import com.codepath.flickster.fragments.UpcomingFragment;
import com.codepath.flickster.requests.ConfigurationRequest;
import com.codepath.flickster.util.FlicksterConstants;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.ResponseHandlerInterface;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class MovieActivity extends AppCompatActivity {

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;

    private ResponseHandlerInterface configResponseHandler = new JsonHttpResponseHandler() {
        @Override
        public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
            super.onSuccess(statusCode, headers, response);
            try {
                String imageBaseURL = response.getJSONObject("images").getString("base_url");
                FlicksterConstants.setImageBaseURL(imageBaseURL);
            } catch (JSONException e) {
                Log.e(MovieActivity.class.getName(), "Error getting configuration.", e);
            }

            setUpTabs();
        }

        @Override
        public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
            super.onFailure(statusCode, headers, throwable, errorResponse);
            Log.e(MovieActivity.class.getName(), "Error getting configuration", throwable);
        }
    };

    class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new NowPlayingFragment();
                case 1:
                    return new PopularFragment();
                case 2:
                    return new UpcomingFragment();
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);

        new ConfigurationRequest().execRequest(configResponseHandler);

    }

    protected void setUpTabs() {
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.now_playing)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.most_popular)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.coming_soon)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}
