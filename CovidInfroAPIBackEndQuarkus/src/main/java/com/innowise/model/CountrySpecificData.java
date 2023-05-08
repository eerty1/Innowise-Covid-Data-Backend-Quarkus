package com.innowise.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class CountrySpecificData {

    @Getter(onMethod_ = @JsonGetter("Country"))
    @Setter(onMethod_ = @JsonSetter("Country"))
    private String country;

    @Getter(onMethod_ = @JsonGetter("Cases"))
    @Setter(onMethod_ = @JsonSetter("Cases"))
    private int cases;

    @Getter(onMethod_ = @JsonGetter("Date"))
    @Setter(onMethod_ = @JsonSetter("Date"))
    private String date;
}
