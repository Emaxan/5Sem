package by.bsuir.lab03.controller;

import by.bsuir.lab03.controller.specific.AdminController;
import by.bsuir.lab03.controller.specific.GuestController;
import by.bsuir.lab03.controller.specific.ClientController;

public class ControllerFactory {
    private static final ControllerFactory factory = new ControllerFactory();

    private final GuestController guestController = new GuestController();
    private final ClientController clientController = new ClientController();
    private final AdminController adminController = new AdminController();

    private ControllerFactory() {}

    public static ControllerFactory getInstance() {
        return factory;
    }

    public GuestController getGuestController() {
        return guestController;
    }

    public ClientController getClientController() {
        return clientController;
    }

    public AdminController getAdminController() {
        return adminController;
    }
}
