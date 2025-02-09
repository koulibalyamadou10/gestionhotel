package gn.hotel.panels;

import gn.hotel.base.KPanel;
import gn.hotel.interfaces.IClient;
import gn.hotel.models.Client;

import javax.swing.*;

public class ClientPanel extends KPanel implements IClient {

    public ClientPanel() {
        super();
        add(new JLabel("Client Panel"));
    }

    @Override
    public void createClient(Client client) {

    }

    @Override
    public void updateClient(Client client) {

    }

    @Override
    public void deleteClient(Client client) {

    }

    @Override
    public void viewClient(Client client) {

    }
}
