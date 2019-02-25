package com.omerilhanli.tmdbmove.data.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "maximum",
        "minimum"
})
public class Dates {

    @JsonProperty("maximum")
    public String maximum;

    @JsonProperty("minimum")
    public String minimum;

    @Override
    public String toString() {
        return "Dates{" +
                "maximum='" + maximum + '\'' +
                ", minimum='" + minimum + '\'' +
                '}';
    }
}
