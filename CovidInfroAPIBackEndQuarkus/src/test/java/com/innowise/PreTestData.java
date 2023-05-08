package com.innowise;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.model.AvailableCountry;
import com.innowise.model.CountrySpecificData;
import com.innowise.model.RequiredCountries;
import jakarta.inject.Singleton;

import java.util.List;

@Singleton
public class PreTestData {

    private final ObjectMapper postDataMapper = new ObjectMapper();

    public String postRequiredCountriesForSuccessfulRequest() throws JsonProcessingException {
        return postDataMapper.writeValueAsString(new RequiredCountries(
                List.of(new AvailableCountry("Belarus", "belarus", "BY"),
                        new AvailableCountry("Russian Federation", "russian-federation", "RU")
                ),
                "2023-01-20T00:00:00Z",
                "2023-03-01T00:00:00Z"
        ));
    }

    public String postRequiredCountriesForFailedRequestTooManyRequests() throws JsonProcessingException {
        return postDataMapper.writeValueAsString(new RequiredCountries(
                List.of(
                        new AvailableCountry("Czech Republic", "czech-republic", "CZ"),
                        new AvailableCountry("Israel", "israel", "IL"),
                        new AvailableCountry("Jordan", "jordan", "JO"),
                        new AvailableCountry("Latvia", "latvia", "LV"),
                        new AvailableCountry("India", "india", "IN"),
                        new AvailableCountry("Albania", "albania", "AL")
                ),
                "2023-01-20T00:00:00Z",
                "2023-03-01T00:00:00Z"
        ));
    }

    public String postRequiredCountriesForFailedRequestFaultyData() throws JsonProcessingException {
        return postDataMapper.writeValueAsString(new RequiredCountries(
                List.of(new AvailableCountry("Cayman Islands", "cayman-islands", "KY")
                ),
                "2023-01-20T00:00:00Z",
                "2023-03-01T00:00:00Z"
        ));
    }
}
