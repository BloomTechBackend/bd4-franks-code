package com.frank.client;

import com.frank.client.services.model.Movie;
import com.frank.client.services.MovieAPIService;
import com.frank.emojis.Emogis;

public final class FindMovie implements Runnable {

    private Movie foundMovie;
    private int   movieNumberWanted;

    private MovieAPIService movieServices = new MovieAPIService();
    public void run() {
        long processStartTime = System.currentTimeMillis();
        foundMovie = movieServices.getMovie(movieNumberWanted);    // Call non-caching service so it takes some time
        long processingTime = System.currentTimeMillis() - processStartTime;
        System.out.println(Emogis.ALARM_CLOCK +" It took " + processingTime + " milliseconds to find Movie Number " + movieNumberWanted);
//        System.out.println("Movie #: " + movieNumberWanted + "\n" + foundMovie);
    }

    public Movie getFoundMovie() {
        return foundMovie;
    }

    public void setMovieNumberWanted(int movieNumberWanted) {
        this.movieNumberWanted = movieNumberWanted;
    }
}
