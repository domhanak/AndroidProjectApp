package cz.muni.fi.pv256.movio.uco410430.domain;

/**
 * Simple domain class representing Movie.
 *
 * @author Dominik Hanak
 */
public class Movie {
    private long releaseDate;
    private String coverPath;
    private String title;

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
}
