package com.amazon.popmovies;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by jiamingm on 11/29/16.
 */
public class Movie implements Parcelable {
    private int id;
    private String posterPath;
    private String originalTitle;
    private String overview;
    private double voteAverage;
    private Date releaseDate;

    public Movie() {}

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeString(posterPath);
        parcel.writeString(originalTitle);
        parcel.writeString(overview);
        parcel.writeDouble(voteAverage);
        parcel.writeLong(releaseDate.getTime());
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    private Movie(Parcel parcel) {
        id = parcel.readInt();
        posterPath = parcel.readString();
        originalTitle = parcel.readString();
        overview = parcel.readString();
        voteAverage = parcel.readDouble();
        releaseDate = new Date(parcel.readLong());
    }
}
