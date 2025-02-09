package gn.hotel.interfaces;

import gn.hotel.models.Client;

public interface IClient {

    public abstract void createClient(Client client);
    public abstract void updateClient(Client client);
    public abstract void deleteClient(Client client);
    public abstract void viewClient(Client client);

}
