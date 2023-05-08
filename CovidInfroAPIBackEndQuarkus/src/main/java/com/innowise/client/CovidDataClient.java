package com.innowise.client;

import com.innowise.exception.CovidDataResponseExceptionMapper;
import com.innowise.model.AvailableCountry;
import com.innowise.model.CountrySpecificData;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.annotation.RegisterProvider;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient(configKey = "covid-data-service")
@RegisterProvider(CovidDataResponseExceptionMapper.class)
public interface CovidDataClient {
    @GET
    @Path("/countries")
    Uni<List<AvailableCountry>> getAvailableCountries();

    @GET
    @Path("/country/{slug}/status/confirmed/live")
    Uni<List<CountrySpecificData>> getCountrySpecificData(@PathParam("slug") String slug, @QueryParam("from") String dateFrom, @QueryParam("to") String dateTo);
}
