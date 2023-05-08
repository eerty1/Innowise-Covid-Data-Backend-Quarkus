package com.innowise.service;

import com.innowise.client.CovidDataClient;
import com.innowise.model.AvailableCountry;
import com.innowise.model.CountrySpecificData;
import com.innowise.model.MinMaxStatisticsResponse;
import com.innowise.model.RequiredCountries;
import com.innowise.comparator.CasesComparator;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import io.smallrye.mutiny.groups.UniJoin;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.List;
import java.util.NoSuchElementException;

import static java.util.Collections.max;
import static java.util.Collections.min;

@Singleton
public class CovidDataService {
    private static final CasesComparator CASES_COMPARATOR = new CasesComparator();
    @ConfigProperty(name = "com.innowise.faultydata.exception.message")
    String faultyDataExceptionMessage;
    @Inject
    @RestClient
    CovidDataClient covidDataClient;

    public Uni<MinMaxStatisticsResponse> respondWithMinMaxStatistics(RequiredCountries requiredCountries) {
        Uni<List<List<CountrySpecificData>>> requiredCountriesData = collectDataForRequiredCountries(requiredCountries);
        Multi<MinMaxStatisticsResponse> minMaxStatisticsParticularCountry = findMinMaxStatisticsForParticularCountry(requiredCountriesData);
        return calculateMinMaxStatisticsResponse(minMaxStatisticsParticularCountry);
    }

    private Uni<MinMaxStatisticsResponse> calculateMinMaxStatisticsResponse(Multi<MinMaxStatisticsResponse> minMaxStatisticsForParticularCountry) {
        MinMaxStatisticsResponse minMaxStatisticsResponse = new MinMaxStatisticsResponse(0, "", "", 0, "", "");
        return minMaxStatisticsForParticularCountry.collect().asList().flatMap(minMaxStatisticsSingleCountry -> {

            for (MinMaxStatisticsResponse minMax : minMaxStatisticsSingleCountry) {
                if (minMaxStatisticsResponse.getMaxCasesAmount() < minMax.getMaxCasesAmount()) {
                    minMaxStatisticsResponse.setMaxCasesAmount(minMax.getMaxCasesAmount());
                    minMaxStatisticsResponse.setMaxCasesCountryName(minMax.getMaxCasesCountryName());
                    minMaxStatisticsResponse.setMaxCasesDate(minMax.getMaxCasesDate());
                }

                if (minMaxStatisticsResponse.getMinCasesAmount() == 0 || minMaxStatisticsResponse.getMinCasesAmount() > minMax.getMinCasesAmount()) {
                    minMaxStatisticsResponse.setMinCasesAmount(minMax.getMinCasesAmount());
                    minMaxStatisticsResponse.setMinCasesCountryName(minMax.getMinCasesCountryName());
                    minMaxStatisticsResponse.setMinCasesDate(minMax.getMinCasesDate());
                }
            }
            return Uni.createFrom().item(minMaxStatisticsResponse);
        });
    }

    private Multi<MinMaxStatisticsResponse> findMinMaxStatisticsForParticularCountry(Uni<List<List<CountrySpecificData>>> countriesDataStorage) {
        Multi<List<CountrySpecificData>> singleCountrySpecificData = countriesDataStorage
                .onItem()
                .transformToMulti(singleCountryData -> Multi.createFrom().iterable(singleCountryData));

        return singleCountrySpecificData.flatMap((singleCountryCases -> {
            if (!singleCountryCases.isEmpty()) {
                return Multi.createFrom().item(
                        new MinMaxStatisticsResponse(
                                min(singleCountryCases, CASES_COMPARATOR).getCases(),
                                min(singleCountryCases, CASES_COMPARATOR).getCountry(),
                                min(singleCountryCases, CASES_COMPARATOR).getDate(),
                                max(singleCountryCases, CASES_COMPARATOR).getCases(),
                                max(singleCountryCases, CASES_COMPARATOR).getCountry(),
                                max(singleCountryCases, CASES_COMPARATOR).getDate()));
            } else {
                throw new NoSuchElementException(faultyDataExceptionMessage);
            }
        }));
    }

    private Uni<List<List<CountrySpecificData>>> collectDataForRequiredCountries(RequiredCountries requiredCountries) {
        UniJoin.Builder<List<CountrySpecificData>> countriesDataStorageBuilder = Uni.join().builder();

        for (AvailableCountry availableCountry : requiredCountries.getAvailableCountries()) {
            Uni<List<CountrySpecificData>> singleCountryData = covidDataClient
                    .getCountrySpecificData(availableCountry.getSlug(), requiredCountries.getDateFrom(), requiredCountries.getDateTo());

            countriesDataStorageBuilder.add(singleCountryData);
        }

        return countriesDataStorageBuilder.joinAll().andFailFast();
    }
}
