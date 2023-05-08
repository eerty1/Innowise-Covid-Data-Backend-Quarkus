package com.innowise.exception;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;

@Provider
public class CovidDataExceptionMapper implements ExceptionMapper<RuntimeException> {
    @ConfigProperty(name = "com.innowise.toomanyrequests.exception.message")
    String tooManyRequestsExceptionMessage;
    @ConfigProperty(name = "com.innowise.provideapitemporaryunavailable.exception.message")
    String providerAPITemporaryUnavailableExceptionMessage;
    @ConfigProperty(name = "com.innowise.faultydata.exception.message")
    String faultyDataExceptionMessage;

    @Override
    public Response toResponse(RuntimeException runtimeException) {
        if (runtimeException.getMessage().contains(tooManyRequestsExceptionMessage)) {
            return Response.status(Response.Status.TOO_MANY_REQUESTS).entity(tooManyRequestsExceptionMessage).build();
        }

        if (runtimeException.getMessage().contains(providerAPITemporaryUnavailableExceptionMessage)) {
            return Response.status(Response.Status.SERVICE_UNAVAILABLE).entity(providerAPITemporaryUnavailableExceptionMessage).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(faultyDataExceptionMessage).build();
    }
}
