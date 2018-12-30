package com.utopusinsights.test.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Wrapper class for the input file
 */
public class Input {
    @JsonProperty("list")
    private InputLists lists;

    public InputLists getLists() {
        return lists;
    }

    public void setLists(InputLists lists) {
        this.lists = lists;
    }

    @Override
    public String toString() {
        return "Input{" +
                "lists=" + lists +
                '}';
    }
}
