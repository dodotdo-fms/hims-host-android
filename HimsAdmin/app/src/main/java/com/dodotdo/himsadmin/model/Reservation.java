package com.dodotdo.himsadmin.model;

/**
 * Created by Omjoon on 16. 4. 20..
 */
public class Reservation {
    String arrivalDate;
    String departureDate;
    String checkinTime;
    String checkoutTime;
    Guest guest;

    public String getArrivalDate() {
        return arrivalDate;
    }

    public String getDepartureDate() {
        return departureDate;
    }

    public String getCheckinTime() {
        return checkinTime;
    }

    public String getCheckoutTime() {
        return checkoutTime;
    }

    public Guest getGuest() {
        return guest;
    }
}
