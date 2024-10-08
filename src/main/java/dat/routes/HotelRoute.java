package dat.routes;

import dat.controllers.impl.HotelController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class HotelRoute {

    private final HotelController hotelController = new HotelController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/", hotelController::create, Role.USER, Role.ADMIN);
            get("/", hotelController::readAll, Role.ANYONE);
            get("/{id}", hotelController::read, Role.ANYONE);
            put("/{id}", hotelController::update, Role.ADMIN);
            delete("/{id}", hotelController::delete, Role.USER, Role.ADMIN);
        };
    }
}
