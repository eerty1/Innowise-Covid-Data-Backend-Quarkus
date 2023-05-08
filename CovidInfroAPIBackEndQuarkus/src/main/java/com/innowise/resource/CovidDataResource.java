package com.innowise.resource;

import com.innowise.client.CovidDataClient;
import com.innowise.model.AvailableCountry;
import com.innowise.model.MinMaxStatisticsResponse;
import com.innowise.model.RequiredCountries;
import com.innowise.service.CovidDataService;
import io.smallrye.mutiny.Uni;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;

@Path("covid-data")
public class CovidDataResource {
    @Inject
    @RestClient
    CovidDataClient covidDataClient;

    @Inject
    CovidDataService covidDataService;

    @GET
    public Uni<List<AvailableCountry>> getAvailableCountries() {
        return covidDataClient.getAvailableCountries();
    }

    @POST
    public Uni<MinMaxStatisticsResponse> getMinMaxStatisticsResponse(RequiredCountries requiredCountries) {
        return covidDataService.respondWithMinMaxStatistics(requiredCountries);
    }
}
