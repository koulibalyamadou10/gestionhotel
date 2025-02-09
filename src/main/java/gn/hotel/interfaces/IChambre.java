package gn.hotel.interfaces;

import gn.hotel.models.Chambre;

public interface IChambre {

    void createChambre(Chambre chambre);
    void updateChambre(Chambre chambre);
    void deleteChambre(Chambre chambre);
    void viewChambre(Chambre chambre);
}
