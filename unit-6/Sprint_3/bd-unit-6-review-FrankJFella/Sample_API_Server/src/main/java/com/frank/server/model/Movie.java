package com.frank.server.model;

import java.time.LocalDate;
import java.util.Objects;

// Represents the data returned from MovieDb API call for a movie
// All we need to idenbtify is data we want to store
// The member variable names must match the corressponding JSON attribute names
public class Movie {
    private int       movieNumber;  // asigned by this app - does not come API call
    private int       id;
    private Boolean   adult;
    private String    title;
    private LocalDate release_date;
    private String    overview;

    public Movie() {}

    public int getMovieNumber() {
        return movieNumber;
    }

    public void setMovieNumber(int movieNumber) {
        this.movieNumber = movieNumber;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDate getRelease_date() {
        return release_date;
    }

    public void setRelease_date(LocalDate release_date) {
        this.release_date = release_date;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getMovieNumber() == movie.getMovieNumber() && getId() == movie.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieNumber(), getId());
    }
} // End of Movie class
