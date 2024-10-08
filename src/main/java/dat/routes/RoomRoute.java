package dat.routes;

import dat.controllers.impl.RoomController;
import dat.security.enums.Role;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class RoomRoute {

    private final RoomController roomController = new RoomController();

    protected EndpointGroup getRoutes() {

        return () -> {
            post("/hotel/{id}", roomController::create, Role.USER, Role.ADMIN);
            get("/", roomController::readAll, Role.ANYONE);
            get("/{id}", roomController::read, Role.ANYONE);
            put("/{id}", roomController::update, Role.ADMIN);
            delete("/{id}", roomController::delete, Role.USER, Role.ADMIN);
        };
    }
}
