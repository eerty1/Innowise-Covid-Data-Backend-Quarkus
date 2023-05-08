package com.innowise.model;

import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonSetter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
public class AvailableCountry {

    @Getter(onMethod_ = @JsonGetter("Country"))
    @Setter(onMethod_ = @JsonSetter("Country"))
    private String country;

    @Getter(onMethod_ = @JsonGetter("Slug"))
    @Setter(onMethod_ = @JsonSetter("Slug"))
    private String slug;

    @Getter(onMethod_ = @JsonGetter("ISO2"))
    @Setter(onMethod_ = @JsonSetter("ISO2"))
    private String iso2;

}