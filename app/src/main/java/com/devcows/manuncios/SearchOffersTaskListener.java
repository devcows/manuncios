package com.devcows.manuncios;

import java.util.List;

public interface SearchOffersTaskListener {
    public void onOffersGetResult(List<Offer> offers);
}