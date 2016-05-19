package com.codepath.flickster.fragments;

import com.codepath.flickster.R;
import com.codepath.flickster.requests.NowPlayingRequest;
import com.codepath.flickster.requests.UpcomingRequest;
import com.codepath.flickster.requests.base.BaseFlicksterRequest;

public class UpcomingFragment extends BaseFragment {

    public UpcomingFragment() {
        // Required empty public constructor
    }

    public int getLayout() {
        return R.layout.fragment_upcoming;
    }

    public BaseFlicksterRequest getRequest() {
        return new UpcomingRequest();
    }

    public int getListViewId() {
        return R.id.lvMoviesUpcoming;
    }

    public int getSwipeContainerLayout() {
        return R.id.swipeContainerUpcoming;
    }
}
