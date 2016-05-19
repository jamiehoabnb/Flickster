package com.codepath.flickster.fragments;

import com.codepath.flickster.R;
import com.codepath.flickster.requests.NowPlayingRequest;
import com.codepath.flickster.requests.base.BaseFlicksterRequest;

public class NowPlayingFragment extends BaseFragment {

    public NowPlayingFragment() {
        // Required empty public constructor
    }

    public int getLayout() {
        return R.layout.fragment_now_playing;
    }

    public BaseFlicksterRequest getRequest() {
        return new NowPlayingRequest();
    }

    public int getListViewId() {
        return R.id.lvMoviesNowPlaying;
    }

    public int getSwipeContainerLayout() {
        return R.id.swipeContainerNowPlaying;
    }
}
