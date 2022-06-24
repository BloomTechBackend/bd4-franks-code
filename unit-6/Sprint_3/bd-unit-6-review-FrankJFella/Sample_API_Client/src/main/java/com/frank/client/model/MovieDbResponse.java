package com.frank.client.model;

import com.frank.client.services.model.Movie;

import java.util.Arrays;
import java.util.Objects;

public class MovieDbResponse {

        long page;
        Movie[] results;

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

