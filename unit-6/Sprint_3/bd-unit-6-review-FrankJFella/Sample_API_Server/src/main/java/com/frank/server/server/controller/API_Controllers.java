package com.frank.server.server.controller;

import com.frank.server.dao.MovieDao;
import com.frank.server.model.Movie;
import com.frank.server.exception.NotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController   // Tells the server there are controllers to handle RESTful API calls for specific URLS
@RequestMapping(path="/movie")   // Base path for all URLs this class handles is "/movie"
public class API_Controllers {

    static final private MovieDao theMovieDao = new MovieDao();

    private long controllerStartTime;
    private long controllerEndTime;

    @GetMapping (value={"/", ""})  // handle the "/movie/"  or "/movie" URL
    // Receiving an HttpServlet request as the first parameter to controllers give you access to request info
    public List<Movie> rootPathProcessor(HttpServletRequest theHttpRequest) {
        logHttpRequest(theHttpRequest);    // Pass the request info to the logging method
        List<Movie> allMovies = theMovieDao.getAllMovies();
        long endTime   = System.currentTimeMillis();
        logEndOfProcessInformation();
        return allMovies;
    }
    @GetMapping (value="/find")  // handle "/movie/find" URL
    // @RequestParam will get the UTL
    public Movie findMoveById(HttpServletRequest theHttpRequest, @RequestParam int id) throws NotFoundException, InterruptedException {
        logHttpRequest(theHttpRequest);

        Movie aMovie = theMovieDao.findMovie(id);

        logMessage("Movie "+ id + " was" + ((aMovie == null)  ? " NOT, REPEAT NOT" : "") + " found");

        logEndOfProcessInformation();

        return aMovie;
    }


    // Log request with timestamp and information from the Http Request received by the server
    private void logHttpRequest(HttpServletRequest theRequest) {
        controllerStartTime = System.currentTimeMillis();

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);

        System.out.println("-".repeat(100));
        System.out.printf("%s --> %4s %4s request for URL: %s%s\n",
                timeNow
                , theRequest.getProtocol()
                , theRequest.getMethod()
                , theRequest.getRequestURI()
                , (theRequest.getQueryString() != null ? ("?" + theRequest.getQueryString()) : ""));
        // the line above will include the query string if there is one
    }
    private void logEndOfProcessInformation() {
        controllerEndTime = System.currentTimeMillis();
        logMessage("Processing time for request: " + (controllerEndTime - controllerStartTime) + " milliseconds");
    }
    // log a message passed in as a parameter
    private void logMessage(String message) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss.A");
        String timeNow = now.format(formatter);

        System.out.printf("%s --> %s\n", timeNow, message);
    }


}
