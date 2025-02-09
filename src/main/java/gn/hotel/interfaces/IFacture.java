package gn.hotel.interfaces;

import gn.hotel.models.Facture;

public interface IFacture {

    void createFacture(Facture facture);
    void updateFacture(Facture facture);
    void deleteFacture(Facture facture);
    void viewFacture(Facture facture);
}
