package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IEmploye;
import gn.hotel.models.Employe;

import javax.swing.*;

public class EmployePanel extends KPanel implements IEmploye {

    public EmployePanel() {
        add(new JLabel("Employee Panel"));
    }

    @Override
    public void createEmploye(Employe employe) {

    }

    @Override
    public void updateEmploye(Employe employe) {

    }

    @Override
    public void deleteEmploye(Employe employe) {

    }

    @Override
    public void viewEmploye(Employe employe) {

    }
}
