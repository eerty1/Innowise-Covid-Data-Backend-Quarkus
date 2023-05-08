package com.innowise.exception;

import jakarta.ws.rs.ServiceUnavailableException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.ext.ResponseExceptionMapper;

@Provider
public class CovidDataResponseExceptionMapper implements ResponseExceptionMapper<RuntimeException> {
    @ConfigProperty(name = "com.innowise.toomanyrequests.exception.message")
    String tooManyRequestsExceptionMessage;
    @ConfigProperty(name = "com.innowise.provideapitemporaryunavailable.exception.message")
    String providerAPITemporaryUnavailableExceptionMessage;

    @Override
    public RuntimeException toThrowable(Response response) {
        switch (response.getStatus()) {
            case (429) -> throw new APISubscriptionException(tooManyRequestsExceptionMessage);
            case (503) -> throw new ServiceUnavailableException(providerAPITemporaryUnavailableExceptionMessage);
        }
        return null;
    }
}