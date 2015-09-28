package com.roodie.model.entities;

import android.text.TextUtils;

import com.google.common.base.Preconditions;
import com.roodie.model.Constants;
import com.roodie.model.util.MoviesCollections;
import com.uwetrottmann.tmdb.entities.TvEpisode;
import com.uwetrottmann.tmdb.entities.TvSeason;

import java.util.Date;
import java.util.List;

import static com.roodie.model.util.TimeUtils.isPastStartingPoint;

/**
 * Created by Roodie on 13.08.2015.
 */
public class SeasonWrapper extends BasicWrapper<SeasonWrapper> {

    Long _id;

    Integer tmdbId;

    long airDate;

    int episodeCount;

    String title;

    String overview;

    Integer seasonNumber;

    String posterUrl;

    transient long lastFullFetchFromTmdbStarted;
    transient long lastFullFetchFromTmdbCompleted;

    transient List<CreditWrapper> cast;
    transient List<CreditWrapper> crew;
    transient List<TvEpisode> episodes;

    public SeasonWrapper() {
    }

    public void setFromSeason(TvSeason season) {

        Preconditions.checkNotNull(season, "Season cannot be null");

        tmdbId = season.id;

        if (_id == null) {
            if (tmdbId != null) {
                _id = new Long(tmdbId.hashCode());
            }
        }

        if (!TextUtils.isEmpty(season.name)) {
            title = season.name;
        }

        if (season.air_date != null) {
            airDate = unbox(airDate, season.air_date);
        }

        if (!TextUtils.isEmpty(season.overview)) {
            overview = season.overview;
        }

        seasonNumber = season.season_number;

        if (!TextUtils.isEmpty(season.poster_path)) {
            posterUrl = season.poster_path;
        }
    }

    private static long unbox(long currentValue, Date newValue) {
        return newValue != null ? newValue.getTime() : currentValue;
    }

    public Long getDbId() {
        return _id;
    }

    public Integer getId() {
        return tmdbId;
    }

    public long getAirDate() {
        return airDate;
    }

    public int getEpisodeCount() {
        return episodeCount;
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public boolean hasPosterUrl() {
        return !TextUtils.isEmpty(posterUrl);
    }

    public List<CreditWrapper> getCast() {
        return cast;
    }

    public List<CreditWrapper> getCrew() {
        return crew;
    }

    public List<TvEpisode> getEpisodes() {
        return episodes;
    }

    public void setCast(List<CreditWrapper> cast) {
        this.cast = cast;
    }

    public void setCrew(List<CreditWrapper> crew) {
        this.crew = crew;
    }

    public void setEpisodes(List<TvEpisode> episodes) {
        this.episodes = episodes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public boolean needFullFetch() {
        return  (!TextUtils.isEmpty(title))
                || (!TextUtils.isEmpty(overview))
                || MoviesCollections.isEmpty(episodes);
    }

    public boolean needFullFetchFromTmdb() {
        return (needFullFetch() || isPastStartingPoint(lastFullFetchFromTmdbCompleted,
                Constants.STALE_MOVIE_DETAIL_THRESHOLD)) &&
                isPastStartingPoint(lastFullFetchFromTmdbStarted,
                        Constants.FULL_MOVIE_DETAIL_ATTEMPT_THRESHOLD);
    }

    public void markFullFetchStarted() {
        lastFullFetchFromTmdbStarted = System.currentTimeMillis();
    }

    public void markFullFetchCompleted() {
        lastFullFetchFromTmdbCompleted = System.currentTimeMillis();
    }

}
