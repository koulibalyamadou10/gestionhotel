package gn.hotel.interfaces;

import gn.hotel.models.Reservation;

public interface IReservation {

    void createReservation(Reservation reservation);
    void updateReservation(Reservation reservation);
    void deleteReservation(Reservation reservation);
    void viewReservation(Reservation reservation);
}
