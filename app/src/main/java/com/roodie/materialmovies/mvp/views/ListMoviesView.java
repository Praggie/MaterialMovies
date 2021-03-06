package com.roodie.materialmovies.mvp.views;

import android.view.View;

import com.arellomobile.mvp.GenerateViewState;
import com.roodie.model.entities.MovieWrapper;

/**
 * Created by Roodie on 13.02.2016.
 */

@GenerateViewState
public interface ListMoviesView extends BaseListView<MovieWrapper> {

    void showItemDetail(MovieWrapper item, View ui);
}
