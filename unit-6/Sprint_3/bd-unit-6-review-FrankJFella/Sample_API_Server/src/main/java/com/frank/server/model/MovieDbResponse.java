package com.frank.server.model;

import java.util.Arrays;
import java.util.Objects;

// Represents the data returned from the MovieDB API call
public class MovieDbResponse {
// Two attributes are returner
        long page;          // Page number returned
        Movie [] results;   // Array of MOvies returned

        public MovieDbResponse() {}

        public long getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public Movie[] getResults() {
            return results;
        }

        public void setResults(Movie[] results) {
            this.results = results;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof MovieDbResponse)) return false;
            MovieDbResponse that = (MovieDbResponse) o;
            return getPage() == that.getPage() && Arrays.equals(getResults(), that.getResults());
        }
    @Override
    public int hashCode() {
        int result1 = Objects.hash(getPage());
        result1 = 31 * result1 + Arrays.hashCode(getResults());
        return result1;
    }

    @Override
    public String toString() {
        return "MovieDbResponse{" +
                "pageNumber=" + page +
                ", result=" + Arrays.toString(results) +
                '}';
    } // end of MoveDbResponse class
}

