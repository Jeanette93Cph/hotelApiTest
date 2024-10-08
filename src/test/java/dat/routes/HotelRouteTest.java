package dat.routes;

import dat.PopulatorTest;
import dat.config.ApplicationConfig;
import dat.config.HibernateConfig;
import dat.impl.HotelDAO;
import dat.dtos.HotelDTO;
import dat.entities.Hotel;
import io.javalin.Javalin;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class HotelRouteTest
{
    private static Javalin app;
    private static EntityManagerFactory emf;
    private static String BASE_URL = "http://localhost:7070/api/v1";
    private static HotelDAO hotelDAO = new HotelDAO(emf);
    private static PopulatorTest populator;

    private static HotelDTO h1, h2, h3;
    private static List<HotelDTO> hotels;


    @BeforeAll
    static void setUpAll()
    {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        hotelDAO = new HotelDAO(emf);
        populator = new PopulatorTest(hotelDAO, emf);
        app = ApplicationConfig.startServer(7070);
    }

    @BeforeEach
    void setUp()
    {
        hotels = populator.populateHotels();
        h1 = hotels.get(0);
        h2 = hotels.get(1);
        h3 = hotels.get(2);
    }

    @AfterEach
    void tearDown()
    {
        populator.cleanUpHotels();
    }

    @AfterAll
    static void tearDownAll()
    {
        emf.close();
        ApplicationConfig.stopServer(app);
    }


    @Test
    void postRoute()
    {
        HotelDTO createHotel =
                given()
                .contentType("application/json")
                .body(new HotelDTO("Hotel", "Langt væk", Hotel.HotelType.LUXURY))
                .when()
                .post(BASE_URL + "/hotels/")
                .then()
                .log().all()
                .statusCode(201)
                .extract()
                .as(HotelDTO.class);

        assertThat(createHotel, is(notNullValue()));
        assertThat(createHotel, is(instanceOf(HotelDTO.class)));

        String actual = createHotel.getHotelName();

        assertThat(actual, containsString("Hotel"));
    }


    @Test
    void getAllRoutes()
    {
        HotelDTO[] listOfhotels =
                given()
                .when()
                .get(BASE_URL + "/hotels")
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(HotelDTO[].class);

        assertEquals(3, listOfhotels.length);
        assertThat(listOfhotels, arrayContainingInAnyOrder(h1, h2, h3));
    }


    @Test
    void getRoute()
    {
        HotelDTO hotel =
                given()
                .when()
                .get(BASE_URL + "/hotels/" + h1.getId())
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(HotelDTO.class);

        assertThat(hotel, equalTo(h1));
    }


    //not working
    @Test
    void putRoute()
    {
        h1.setHotelName("Badehotellet");

        HotelDTO updatedHotel =
                given()
                .contentType("application/json")
                .body(h1)
                .when()
                .put(BASE_URL + "/hotels/" + h1.getId())
                .then()
                .log().all()
                .statusCode(200)
                .extract()
                .as(HotelDTO.class);

        String expectedName = "Badehotellet";

        assertThat(updatedHotel.getHotelName(), is(expectedName));
    }


    //not working
    @Test
    void deleteRoute()
    {
        HotelDTO hotel =
                given()
                        .when()
                        .delete(BASE_URL + "/hotels/" + h1.getId())
                        .then()
                        .log().all()
                        .statusCode(204)
                        .extract()
                        .as(HotelDTO.class);

        assertThat(hotel, is(nullValue()));
    }

}