package com.amazon.popmovies;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by jiamingm on 11/29/16.
 */
public class Movie implements Serializable {
    private int id;
    private String posterPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private Date releaseDate;

    public void setId(final int id) {
        this.id = id;
    }

    public void setPosterPath(final String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOriginalTitle(final String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public void setOverview(final String overview) {
        this.overview = overview;
    }

    public void setVoteAverage(final double voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setReleaseDate(final Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }
}
