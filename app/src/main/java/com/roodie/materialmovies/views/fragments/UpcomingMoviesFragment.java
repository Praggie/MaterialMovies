package com.roodie.materialmovies.views.fragments;

import android.os.Bundle;

import com.arellomobile.mvp.presenter.InjectPresenter;
import com.roodie.materialmovies.mvp.presenters.ListMoviesPresenter;
import com.roodie.materialmovies.views.fragments.base.MoviesGridFragment;
import com.roodie.model.Display;

/**
 * Created by Roodie on 02.08.2015.
 */
public class UpcomingMoviesFragment extends MoviesGridFragment {

    private static final String LOG_TAG = UpcomingMoviesFragment.class.getSimpleName();

    @InjectPresenter
    ListMoviesPresenter mMoviesPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void attachUiToPresenter() {
        mMoviesPresenter.onUiAttached(this, getQueryType(), null);
        Display display = getDisplay();
        if ( display != null) {
            display.showUpNavigation(getQueryType() != null && getQueryType().showUpNavigation());

        }
    }

    @Override
    public void onScrolledToBottom() {
        super.onScrolledToBottom();
        mMoviesPresenter.onScrolledToBottom(this, getQueryType());
    }

    @Override
    public void onRefreshData(boolean visible) {
        super.onRefreshData(visible);
        mMoviesPresenter.refresh(this, getQueryType());
    }

    @Override
    public MMoviesQueryType getQueryType() {
        return MMoviesQueryType.UPCOMING_MOVIES;
    }


}
