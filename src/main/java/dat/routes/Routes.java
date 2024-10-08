package dat.routes;

import dat.security.routes.SecurityRoutes;
import io.javalin.apibuilder.EndpointGroup;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Routes {

    private final HotelRoute hotelRoute = new HotelRoute();
    private final RoomRoute roomRoute = new RoomRoute();

    public EndpointGroup getRoutes() {
        return () -> {
                path("/hotels", hotelRoute.getRoutes());
                path("/rooms", roomRoute.getRoutes());
                path("/auth", SecurityRoutes.getSecurityRoutes());
                path("/protected", SecurityRoutes.getSecuredRoutes());
        };
    }
}
