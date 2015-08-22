/*
 * Copyright 2014 Chris Banes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.roodie.model;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.view.View;

import com.roodie.model.entities.MovieWrapper;
import com.roodie.model.entities.PersonWrapper;

public interface Display {

    public static final String PARAM_ID = "_id";

    public static final String PARAM_LOCATION = "_location";

    public static final String PARAM_IMAGE = "_image";

    public void showMovies();

    public void showTvShows();

    public void showSettings();

    public void showAbout();

    public void startMovieDetailActivityByAnimation(String movieId, int[] startingLocation);

    public void showMovieDetailFragmentByAnimation(String movieId, int[] startingLocation);

    public void startMovieDetailActivityByAnimation(String movieId, View view, String imageUrl, int[] startingLocation);

    public void showMovieDetailFragmentByAnimation(String movieId, int[] startingLocation, String imageUrl);

    public void startMovieDetailActivityBySharedElements(String movieId, View view, String imageUrl);

    public void showMovieDetailFragmentBySharedElements(String movieId, String imageUrl);

    public void startPersonDetailActivity(String id, Bundle bundle);

    public void showPersonDetailFragment(String id);

    public void startPersonDetailActivity(String id, int[] startingLocation);

    public void showPersonDetailFragment(String id, int[]startingLocation);

    public void startMovieImagesActivity(String movieId);

    public void showMovieImagesFragment(String movieId);

    public void startSettingsActivity();

    public void closeDrawerLayout();

    public boolean hasMainFragment();

    public void showUpNavigation(boolean show);

    public void setActionBarTitle(CharSequence title);

    public void setActionBarSubtitle(CharSequence title);

    public boolean popEntireFragmentBackStack();

    public void finishActivity();

    public void playYoutubeVideo(String id);

    public boolean toggleDrawer();

    public void setStatusBarColor(float scroll);

    public void setSupportActionBar(Object toolbar, boolean handleBackground);

    public void shareMovie(int movieId, String movieTitle);

    public void shareTvShow(int showId, String showTitle);

    public void startShareIntentChooser(String message, @StringRes int titleResId);

    public void openTmdbMovie(MovieWrapper movie);

    public void openTmdbPerson(PersonWrapper person);

    public void openTmdbUrl(String url);

    public boolean tryStartActivity(Intent intent, boolean displayError);

    public void performWebSearch(String query);

    public void showSearchFragment();


}
