package com.frank.server.dao;

import com.frank.server.model.Movie;
import com.frank.server.datastore.MovieDataStore;

import java.util.List;

public class MovieDao {

    static final MovieDataStore movieDataBase;  // Hold the movies retrieved from external API

    static {
        movieDataBase = new MovieDataStore();  // Get the movies and assign to our database
    }

    public List<Movie> getAllMovies() {
        return movieDataBase.getAllMovies();
    }

    public Movie findMovie(int movieId) throws InterruptedException {
        return movieDataBase.findMovie(movieId);
    }
} // End of MovieDao class

