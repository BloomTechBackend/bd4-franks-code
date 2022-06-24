package com.frank.client;

import com.frank.client.services.MovieAPICachingService;
import com.frank.client.services.MovieAPIService;
import com.frank.client.services.model.Movie;
import com.frank.emojis.Emogis;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class API_Client_Application {

   private static MovieAPIService        movieServices       = new MovieAPIService();
   private static MovieAPICachingService movieCachedServices = new MovieAPICachingService(movieServices);

   private static long processStartTime   = 0;
   private static long processEndTime     = 0;
   private static long processElapsedTime = 0;

   private static Scanner theKeyBoard = new Scanner(System.in);

   public static void main(String[] args) throws FileNotFoundException, InterruptedException {

      System.out.println("-".repeat(80) + "\nWelcome to Unit 6 Review Client Application\n"+ "-".repeat(80));

      System.out.println("Attempting to get all movies...");
      processStartTime = System.currentTimeMillis();

      List<Movie> theMovies = movieServices.getAllMovies();

      processEndTime = System.currentTimeMillis();
      processElapsedTime = processEndTime - processStartTime;
      System.out.println(theMovies.size() + " Movies received");
      displayProcessingTime();

      boolean shouldLoop = true;

      while(shouldLoop) {
         System.out.println(Emogis.MOVIE_CAMERA.repeat(50)+"\nEnter movie number to find or 0 to end to move to next process");
         String userInput = theKeyBoard.nextLine();
         switch(userInput) {
            case "0": {
               shouldLoop = false;
               break;
            }
            default: {
               displaySpecificMovieByMovieNumber(userInput);
               break;
            }
         }
      }
      System.out.println(Emogis.MOVIE_CAMERA.repeat(50)+"\nNow going to find Movies indicated in a collection of Movie Numbers Using Threads");
      displaySetOfMovies();
      System.out.println("\nOk, threads started,program terminating\n"+ Emogis.MOVIE_CAMERA.repeat(50));

      return;

} // end of main()

   /************************************************************************************************************
    * Find Movie indicated by userInput value
    *
    * @param userInput
    */
   private static void displaySpecificMovieByMovieNumber(String userInput) {
      int movieNumberWanted = 0;
      Movie foundMovie;

      movieNumberWanted = Integer.parseInt(userInput);
      System.out.println("Looking for Movie Number " + movieNumberWanted);
      processStartTime = System.currentTimeMillis();

      //foundMovie = movieServices.getMovie(movieNumberWanted);          // Call non-caching service
      foundMovie = movieCachedServices.getMovie(movieNumberWanted);  // Call caching service

      processEndTime = System.currentTimeMillis();
      displayProcessingTime();
      System.out.println("Movie Found: \n" + foundMovie);
   }

   /************************************************************************************************************
    * Find all movies whose Movie Numbers are stored in an array
    * using one thread for each movie search
    *
    * @throws InterruptedException
    */
   private static void displaySetOfMovies() throws InterruptedException {

      int[] moviesWanted = {10, 20, 30, 40};     // Movie numbers of movies to be found
      Movie foundMovie;                          // Hold the Movie found

      for(int movieNumberWanted: moviesWanted) { // Loop thorugh the array of Movie Numbers

         System.out.println("Looking for Movie Number " + movieNumberWanted);

         FindMovie findMovieProcess = new FindMovie();

         findMovieProcess.setMovieNumberWanted(movieNumberWanted);

         Thread aThread = new Thread(findMovieProcess);

         System.out.println("Starting Thread for Movie #: " + movieNumberWanted);

         aThread.start();
      }
   }

   private static void displayProcessingTime() {
      processElapsedTime = processEndTime - processStartTime;
      System.out.println("Process took: " + processElapsedTime + " milliseconds");
   }
}
