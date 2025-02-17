package gn.hotel.interfaces;

import gn.hotel.models.Reservation;

public interface IReservation {

    void createReservation(Reservation reservation);
    void updateReservation(int id);

    void deleteReservation(int id);

    void viewReservation(int id);


}
