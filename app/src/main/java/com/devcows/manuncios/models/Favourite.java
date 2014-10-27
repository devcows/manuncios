package com.devcows.manuncios.models;

import java.io.Serializable;
import java.util.Date;

public class Favourite implements Serializable {
    private Offer offer;
    private Date date_added;

    public Favourite(Offer offer) {
        this.offer = offer;
        this.date_added = new Date();
    }

    public Offer getOffer() {
        return offer;
    }

    @Override
    public String toString() {
        return "Favourite{" +
                "offer=" + offer +
                ", date_added=" + date_added +
                '}';
    }
}
