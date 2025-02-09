package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IFacture;
import gn.hotel.models.Facture;

import javax.swing.*;

public class FacturePanel extends KPanel implements IFacture  {

    public FacturePanel() {
        add(new JLabel("Facture Panel"));
    }

    @Override
    public void createFacture(Facture facture) {

    }

    @Override
    public void updateFacture(Facture facture) {

    }

    @Override
    public void deleteFacture(Facture facture) {

    }

    @Override
    public void viewFacture(Facture facture) {

    }
}
