package com.frank.client.services;

import com.frank.client.services.model.Movie;
import com.frank.client.services.model.MovieAPICachingServiceKey;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import javax.inject.Inject;
import java.util.concurrent.TimeUnit;
public class MovieAPICachingService {

// Define a LoadingCache object for the Cache
// We are using the Google Guava Framework for caching
//
// caching-key   - what we are looking for in the cache (MovieAPICachingKey with Movie Number)
// caching-value - the data to be returned from the request (Movie object)
// define-the-cache(cache-key, cache-value)

    //                    caching-key-type      , cache-value-key
    private LoadingCache<MovieAPICachingServiceKey, Movie>    movieCache;   // reference to the cache

    @Inject
    public MovieAPICachingService(MovieAPIService delegateDao) {
        // instantiate the cache and assign to reference
        this.movieCache = CacheBuilder.newBuilder()
                .maximumSize(200)  // max number of entries for the cache
                .expireAfterWrite(1, TimeUnit.MINUTES)  // Evict after 1 minute since it was written to the cache
                .build(CacheLoader.from(delegateDao::getMovie));  // Go build the cache with delegate method
        //         delegateDao must have a method called getMovie() that receives a "cache-key-object"
    }  // end of ctor

    // method used by application when it wants a movie
    // same name and signature as the original, non-cached method
    // The change to using a cached version of Dao should not require application
    public Movie getMovie(int movieNumber) {
        MovieAPICachingServiceKey cacheKey = new MovieAPICachingServiceKey(movieNumber);
        return movieCache.getUnchecked(cacheKey);  // Use the cache method that will not throw an Exception
    }


} // end of Movie_API_Caching_Service class


