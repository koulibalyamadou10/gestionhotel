package gn.hotel.interfaces;

import gn.hotel.models.Service;

public interface IService {

    void createService(Service service);
    void updateService(Service service);
    void deleteService(Service service);
    void viewService(Service service);
}
