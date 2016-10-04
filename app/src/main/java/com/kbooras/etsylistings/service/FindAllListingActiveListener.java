package com.kbooras.etsylistings.service;

import com.kbooras.etsylistings.model.Result;

import java.util.List;

public interface FindAllListingActiveListener {

    void onResult(List<Result> results);
}
