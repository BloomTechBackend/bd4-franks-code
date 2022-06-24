package com.frank.client.services.model;

import java.util.Objects;

// This the key used for caching
// Not required to make it a separate class since the key is a single value (movieNumber)
// It's a class in this example to simulate the "Real World"
public class MovieAPICachingServiceKey {
    private int movieNumber;

    public MovieAPICachingServiceKey() {}

    public MovieAPICachingServiceKey(int movieNumber) {
        this.movieNumber = movieNumber;
    }

    public int getMovieNumber() {
        return movieNumber;
    }

    public void setMovieNumber(int movieNumber) {
        this.movieNumber = movieNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MovieAPICachingServiceKey)) return false;
        MovieAPICachingServiceKey that = (MovieAPICachingServiceKey) o;
        return getMovieNumber() == that.getMovieNumber();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getMovieNumber());
    }

    @Override
    public String toString() {
        return "MovieAPICachingServiceKey{" +
                "movieNumber=" + movieNumber +
                '}';
    }
}
