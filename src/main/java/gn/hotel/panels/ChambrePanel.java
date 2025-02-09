package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IChambre;
import gn.hotel.models.Chambre;

import javax.swing.*;

public class ChambrePanel extends KPanel implements IChambre {

    public ChambrePanel() {
        add(new JLabel("Chambre Panel"));
    }

    @Override
    public void createChambre(Chambre chambre) {

    }

    @Override
    public void updateChambre(Chambre chambre) {

    }

    @Override
    public void deleteChambre(Chambre chambre) {

    }

    @Override
    public void viewChambre(Chambre chambre) {

    }
}
