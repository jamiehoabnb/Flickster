package com.codepath.flickster.fragments;

import com.codepath.flickster.R;
import com.codepath.flickster.requests.NowPlayingRequest;
import com.codepath.flickster.requests.PopularRequest;
import com.codepath.flickster.requests.base.BaseFlicksterRequest;

public class PopularFragment extends BaseFragment {

    public PopularFragment() {
        // Required empty public constructor
    }

    public int getLayout() {
        return R.layout.fragment_popular;
    }

    public BaseFlicksterRequest getRequest() {
        return new PopularRequest();
    }

    public int getListViewId() {
        return R.id.lvMoviesPopular;
    }

    public int getSwipeContainerLayout() {
        return R.id.swipeContainerPopular;
    }
}
