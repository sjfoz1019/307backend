package com.csc.adbackend;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Error {
    String error;
    List<String> details;

    /* CONSTRUCTORS */

    public Error() {
        this.error = "noError";
        this.details = new ArrayList<String>();
    }

    public Error(String error) {
        this.error = error;
        this.details = new ArrayList<String>();
    }

    public Error(String error, List<String> details) {
        this.error = error;
        this.details = details;
    }

    /* GETTERS AND SETTERS */

    public String getError() {
        return this.error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public List<String> getDetails() {
        return this.details;
    }

    public void setDetails(List<String> details) {
        this.details = details;
    }

    public void addDetail(String detail) {
        this.details.add(detail);
    }

    /* METHODS */

    public boolean noError() {
        return this.error.equals("noError");
    }

    public String jsonify() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }
}
