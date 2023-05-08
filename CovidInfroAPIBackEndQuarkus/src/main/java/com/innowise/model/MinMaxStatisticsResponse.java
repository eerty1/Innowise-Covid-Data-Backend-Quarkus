package com.innowise.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class MinMaxStatisticsResponse {

    @Getter
    @Setter
    private int minCasesAmount;

    @Getter
    @Setter

    private String minCasesCountryName;

    @Getter
    @Setter
    private String minCasesDate;

    @Getter
    @Setter
    private int maxCasesAmount;

    @Getter
    @Setter
    private String maxCasesCountryName;

    @Getter
    @Setter
    private String maxCasesDate;

}
