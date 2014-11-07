//Copyright (C) 2014  Guillermo G. (info@devcows.com)
//
//This program is free software: you can redistribute it and/or modify
//it under the terms of the GNU General Public License as published by
//the Free Software Foundation, either version 3 of the License, or
//(at your option) any later version.
//
//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.
//
//You should have received a copy of the GNU General Public License
//along with this program.  If not, see <http://www.gnu.org/licenses/>.

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
