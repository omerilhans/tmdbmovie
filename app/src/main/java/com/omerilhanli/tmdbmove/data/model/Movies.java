package com.omerilhanli.tmdbmove.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.io.Serializable;
import java.util.List;

/**
 * Created by polbins on 25/02/2017.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "page",
        "results",
        "total_results",
        "total_pages",
        "dates"
})
public class Movies implements Serializable {

    @JsonProperty("page")
    public int page;
    @JsonProperty("results")
    public List<Movie> movies = null;
    @JsonProperty("total_results")
    public int totalResults;
    @JsonProperty("total_pages")
    public int totalPages;
    @JsonProperty("dates")
    public Dates dates;

    @Override
    public String toString() {
        return "Movies{" +
                "page=" + page +
                ", movies=" + movies +
                ", totalResults=" + totalResults +
                ", totalPages=" + totalPages +
                ", dates=" + dates +
                '}';
    }
}

