package dat.config;

import dat.controllers.impl.ExceptionController;
import dat.exceptions.ApiException;
import dat.routes.Routes;
import dat.security.controllers.AccessController;
import dat.security.routes.SecurityRoutes;
import dat.utils.Utils;
import io.javalin.Javalin;
import io.javalin.config.JavalinConfig;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class ApplicationConfig {

    private static Routes routes;
    private static final Logger logger = LoggerFactory.getLogger(ApplicationConfig.class);
    private static final ExceptionController exceptionController = new ExceptionController();
    private static AccessController accessController = new AccessController();

    public static void configuration(JavalinConfig config) {
        config.router.contextPath = "/api/v1"; // base path for all routes
        config.showJavalinBanner = false;
        config.http.defaultContentType = "application/json"; // default content type for requests
        config.router.apiBuilder(routes.getRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecuredRoutes());
        config.router.apiBuilder(SecurityRoutes.getSecurityRoutes());

        // Plugins
        config.bundledPlugins.enableRouteOverview("/routes"); // enables route overview at /routes
    }

    public static String getProperty(String propName) throws IOException
    {
        try (InputStream is = HibernateConfig.class.getClassLoader().getResourceAsStream("properties-from-pom.properties"))
        {
            Properties prop = new Properties();
            prop.load(is);
            return prop.getProperty(propName);
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new IOException("Could not read property from pom file. Build Maven!");
        }
    }

    public static Javalin startServer(int port) {
        routes = new Routes();
        var app = Javalin.create(ApplicationConfig::configuration);
        app.beforeMatched(accessController::accessHandler);
        app.exception(Exception.class, ApplicationConfig::generalExceptionHandler);
        app.exception(ApiException.class, ApplicationConfig::apiExceptionHandler);
        app.start(port);
        return app;
    }

    public static void stopServer(Javalin app) {
        app.stop();
    }

    private static void generalExceptionHandler(Exception e, Context ctx) {
        logger.error("An unhandled exception occurred", e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "error", e.getMessage()));
    }

    public static void apiExceptionHandler(ApiException e, Context ctx) {
        ctx.status(e.getStatusCode());
        logger.warn("An API exception occurred: Code: {}, Message: {}", e.getStatusCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }


}
