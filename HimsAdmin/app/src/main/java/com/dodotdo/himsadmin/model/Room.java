package com.dodotdo.himsadmin.model;

import java.util.List;

/**
 * Created by Omjoon on 16. 4. 20..
 */
public class Room {
    String roomNumber;
    String roomType;
    String floor;
    List<RoomStatus> roomStatus;
    List<Reservation> reservation;
    List<Clean> clean;



    public String getRoomNumber() {
        return roomNumber;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getFloor() {
        return floor;
    }

    public List<RoomStatus> getRoomStatus() {
        return roomStatus;
    }

    public List<Reservation> getReservation() {
        return reservation;
    }

    public List<Clean> getClean() {
        return clean;
    }
}
