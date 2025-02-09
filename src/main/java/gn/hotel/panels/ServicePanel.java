package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IService;
import gn.hotel.models.Service;

import javax.swing.*;

public class ServicePanel extends KPanel implements IService {

    // Constructeur principal
    public ServicePanel() {
        add(new JLabel("Service Panel"));
    }

    @Override
    public void createService(Service service) {

    }

    @Override
    public void updateService(Service service) {

    }

    @Override
    public void deleteService(Service service) {

    }

    @Override
    public void viewService(Service service) {

    }
}
