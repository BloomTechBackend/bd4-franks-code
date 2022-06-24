package com.frank.client.services.model;

import java.time.LocalDate;
import java.util.Objects;

public class Movie {
    private int     movieNumber;
    private int     id;
    private Boolean adult;
    private String  title;
    private LocalDate  release_date;
    private String  overview;

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

    @Override
    public String toString() {
        return  "movieNumber=" + movieNumber +
                "\nid=" + id +
                "\nadult=" + adult +
                "\ntitle='" + title + '\'' +
                "\nrelease_date=" + release_date +
                "\noverview='" + overview + '\'' +
                '}';
    }
} // End of Movie class
