package gn.hotel.models;

public class ReservationService {
    private int idReservation;
    private int idService;

    public ReservationService(){}

    public ReservationService(int idReservation, int idService) {
        this.idReservation = idReservation;
        this.idService = idService;
    }

    // Getters and Setters
    public int getIdReservation() {
        return idReservation;
    }

    public void setIdReservation(int idReservation) {
        this.idReservation = idReservation;
    }

    public int getIdService() {
        return idService;
    }

    public void setIdService(int idService) {
        this.idService = idService;
    }
}
