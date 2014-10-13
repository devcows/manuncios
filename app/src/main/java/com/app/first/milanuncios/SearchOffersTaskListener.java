package com.app.first.milanuncios;

import java.util.List;

public interface SearchOffersTaskListener {
    public void onOffersGetResult(List<Offer> offers);
}
