package com.kirstiebooras.etsylistings.service;

import com.kirstiebooras.etsylistings.model.Result;

import java.util.List;

public interface FindAllFeaturedListingsListener {

    void onResult(List<Result> results);
}
