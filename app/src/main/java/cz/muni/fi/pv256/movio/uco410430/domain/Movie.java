package cz.muni.fi.pv256.movio.uco410430.domain;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Simple domain class representing Movie.
 *
 * @author Dominik Hanak
 */
public class Movie implements Parcelable {
    private long releaseDate;
    private String coverPath;
    private String title;

    public Movie() {
    }

    public Movie(long releaseDate, String coverPath, String title) {
        this.releaseDate = releaseDate;
        this.coverPath = coverPath;
        this.title = title;
    }

    public long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getCoverPath() {
        return coverPath;
    }

    public void setCoverPath(String coverPath) {
        this.coverPath = coverPath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.releaseDate);
        dest.writeString(this.coverPath);
        dest.writeString(this.title);
    }

    private Movie(Parcel in) {
        this.releaseDate = in.readLong();
        this.coverPath = in.readString();
        this.title = in.readString();
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
