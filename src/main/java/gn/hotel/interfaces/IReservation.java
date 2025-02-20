package gn.hotel.interfaces;

import gn.hotel.models.Reservation;

public interface IReservation {

    void createReservation(Reservation reservation);
    void updateReservation(int IDReservation);
    void deleteReservation(int IDReservation);
    void viewReservation(int IDReservation);
}
