package com.omerilhanli.tmdbmove.data.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Configuration {

    @JsonProperty("images")
    public Images images;
    @JsonProperty("change_keys")
    public List<String> changeKeys = null;

    @Override
    public String toString() {
        return "Configuration{" +
                "images=" + images +
                ", changeKeys=" + changeKeys +
                '}';
    }
}
