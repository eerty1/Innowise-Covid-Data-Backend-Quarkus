package com.innowise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
public class RequiredCountries {

    @Getter
    @Setter
    private List<AvailableCountry> availableCountries;

    @Getter
    @Setter
    private String dateFrom;

    @Getter
    @Setter
    private String dateTo;

    @Override
    public String toString() {
        return "RequiredCountries{" + "availableCountries=" + availableCountries + ", dateFrom='" + dateFrom + '\'' + ", dateTo='" + dateTo + '\'' + '}';
    }
}
