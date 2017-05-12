package com.noel201296gmail.cine_matic.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.JsonArray;

import org.json.JSONArray;

/**
 * Created by OMOSEFE NOEL OBASEKI on 04/05/2017.
 */
public class MovieResponse implements Parcelable {

    public static final String TMDB_IMAGE_PATH = "http://image.tmdb.org/t/p/w500";
    private String posterPath;
    private boolean adult;
    private String overview;
    private String releaseDate;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private double popularity;
    private int voteCount;
    private boolean video;
    private double voteAverage;
    private JSONArray genreIds;


    public MovieResponse(String posterPath, boolean adult, String overview, String releaseDate, JSONArray genreIds, int id, String originalTitle, String originalLanguage, String title, String backdropPath, double popularity, int voteCount, boolean video, double voteAverage) {
        this.posterPath = posterPath;
        this.adult = adult;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.genreIds = genreIds;
        this.id = id;
        this.originalTitle = originalTitle;
        this.originalLanguage = originalLanguage;
        this.title = title;
        this.backdropPath = backdropPath;
        this.popularity = popularity;
        this.voteCount = voteCount;
        this.video = video;
        this.voteAverage = voteAverage;
    }


    public MovieResponse(Parcel source) {
        this.posterPath = source.readString();
        this.adult = source.readByte() != 0;
        this.overview = source.readString();
        this.releaseDate = source.readString();
        this.id = source.readInt();
        this.originalTitle = source.readString();
        this.originalLanguage = source.readString();
        this.title = source.readString();
        this.backdropPath = source.readString();
        this.popularity = source.readDouble();
        this.voteCount = source.readInt();
        this.video = source.readByte() != 0;
        this.voteAverage = source.readDouble();
    }
    public String getPosterPath() {
        return TMDB_IMAGE_PATH + posterPath;
    }


    public Boolean getAdult() {
        return adult;
    }



    public String getOverview() {
        return overview;
    }


    public String getReleaseDate() {
        return releaseDate;
    }



    public JSONArray getGenreIds() {
        return genreIds;
    }


    public Integer getId() {
        return id;
    }


    public String getOriginalTitle() {
        return originalTitle;
    }


    public String getOriginalLanguage() {
        return originalLanguage;
    }



    public String getTitle() {
        return title;
    }


    public String getBackdropPath() {
        return  TMDB_IMAGE_PATH + backdropPath;
    }



    public Double getPopularity() {
        return popularity;
    }


    public Integer getVoteCount() {
        return voteCount;
    }


    public Boolean getVideo() {
        return video;
    }


    public Double getVoteAverage() {
        return voteAverage;
    }






    public static final Parcelable.Creator<MovieResponse> CREATOR = new Parcelable.Creator<MovieResponse>() {
        public MovieResponse createFromParcel(Parcel source) {
            MovieResponse movie = new MovieResponse(source);
            return movie;
        }

        public MovieResponse[] newArray(int size) {
            return new MovieResponse[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(posterPath);
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeInt(id);
        dest.writeString(originalTitle);
        dest.writeString(originalLanguage);
        dest.writeString(title);
        dest.writeString(backdropPath);
        dest.writeDouble(popularity);
        dest.writeInt(voteCount);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(voteAverage);
    }


    @Override
    public String toString() {
        return "Movie {posterPath: " + posterPath + ", adult: " + adult
                + ", overview: " + overview + ", releaseDate: " + releaseDate + ", id: "
                + id + ", originalTitle: " + originalTitle + ", originalLanguage: "
                + originalLanguage + ", title: " + title + ", backdropPath: " + backdropPath
                + ", popularity: " + popularity + ", voteCount: "
                + voteCount + ", video: " + video
                + ", voteAverage: " + voteAverage + "}";
    }
}