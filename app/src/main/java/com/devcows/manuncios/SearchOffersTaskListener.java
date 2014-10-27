package com.devcows.manuncios;

import com.devcows.manuncios.models.Offer;

import java.util.List;

public interface SearchOffersTaskListener {
    public void onOffersGetResult(List<Offer> offers);
}
