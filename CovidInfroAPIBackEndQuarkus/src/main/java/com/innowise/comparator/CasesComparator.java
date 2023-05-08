package com.innowise.comparator;

import com.innowise.model.CountrySpecificData;

import java.util.Comparator;

public class CasesComparator implements Comparator<CountrySpecificData> {

    @Override
    public int compare(CountrySpecificData country1, CountrySpecificData country2) {
        return Integer.compare(country1.getCases(), country2.getCases());
    }
}
