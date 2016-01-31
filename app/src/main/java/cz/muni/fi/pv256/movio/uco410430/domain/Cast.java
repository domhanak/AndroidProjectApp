package cz.muni.fi.pv256.movio.uco410430.domain;

import com.google.gson.annotations.SerializedName;

/**
 * Model class for cast to each movie.
 *
 * Created by dhanak on 12/14/15.
 */
public class Cast {

    @SerializedName("cast_id")
    private String mId;

    @SerializedName("profile_path")
    private String mImage;

    @SerializedName("name")
    private String mName;

    public Cast(final String mImage, final String mName) {
        this.mImage = mImage;
        this.mName = mName;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(final String mImage) {
        this.mImage = mImage;
    }

    public String getName() {
        return mName;
    }

    public void setName(final String mName) {
        this.mName = mName;
    }
}