package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IReservation;
import gn.hotel.interfaces.IReservationService;
import gn.hotel.models.Reservation;
import gn.hotel.models.ReservationService;

import javax.swing.*;

public class ResevervationPanel extends KPanel implements IReservation, IReservationService {


    public ResevervationPanel() {
        add(new JLabel("Reservation Panel"));
    }

    // Méthodes concernant le CRUD sur une réservation
    @Override
    public void createReservation(Reservation reservation) {

    }

    @Override
    public void updateReservation(Reservation reservation) {

    }

    @Override
    public void deleteReservation(Reservation reservation) {

    }

    @Override
    public void viewReservation(Reservation reservation) {

    }

    // Méthodes concernant le CRUD sur une réservation service facultatif
    @Override
    public void createReservationService(ReservationService reservationService) {

    }

    @Override
    public void updateReservationService(ReservationService reservationService) {

    }

    @Override
    public void deleteReservationService(ReservationService reservationService) {

    }

    @Override
    public void viewReservationService(ReservationService reservationService) {

    }
}
