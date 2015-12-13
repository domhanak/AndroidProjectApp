package cz.muni.fi.pv256.movio.uco410430.domain;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Simple domain class representing Movie.
 *
 * @author Dominik Hanak
 */
public class Movie implements Parcelable {
    private int id;

    @SerializedName("release_date")
    private String mReleaseDate;

    @SerializedName("poster_path")
    private String mCoverPath;

    @SerializedName("title")
    private String mTitle;

    @SerializedName("backdrop_path")
    private String mBackground;

    @SerializedName("overview")
    private String mOverview;

    public Movie() {
    }

    public Movie(String releaseDate, String coverPath, String title) {
        this.mReleaseDate = releaseDate;
        this.mCoverPath = coverPath;
        this.mTitle = title;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getCoverPath() {
        return mCoverPath;
    }

    public void setCoverPath(String coverPath) {
        this.mCoverPath = coverPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBackground() {
        return mBackground;
    }

    public void setBackground(String background) {
        this.mBackground = background;
    }

    public String getOverview() {
        return mOverview;
    }

    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mCoverPath);
        dest.writeString(this.mTitle);
        dest.writeString(this.mBackground);
        dest.writeString(this.mOverview);
    }

    protected Movie(Parcel in) {
        this.mReleaseDate = in.readString();
        this.mCoverPath = in.readString();
        this.mTitle = in.readString();
        this.mBackground = in.readString();
        this.mOverview = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
