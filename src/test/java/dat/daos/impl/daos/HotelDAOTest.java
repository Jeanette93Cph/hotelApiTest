package dat.daos.impl.daos;

import dat.config.HibernateConfig;
import dat.daos.impl.HotelDAO;
import dat.daos.impl.PopulatorTest;
import dat.dtos.HotelDTO;
import dat.dtos.RoomDTO;
import dat.entities.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HotelDAOTest
{

    private static EntityManagerFactory emf;
    private static HotelDAO hotelDAO = new HotelDAO(emf);
    private static PopulatorTest populator;

    private static List<HotelDTO> hotels;


    @BeforeAll
    static void setUpAll()
    {
        emf = HibernateConfig.getEntityManagerFactoryForTest();
        hotelDAO = new HotelDAO(emf);
        populator = new PopulatorTest(hotelDAO, emf);
    }


    @BeforeEach
    void setUp()
    {
        hotels = populator.populateHotels();
    }


    @AfterAll
    static void tearDown()
    {
        emf.close();
    }


    @Test
    void readAll()
    {
        int expected = 3;
        int actual = hotels.size();
        List<HotelDTO> vishoteller = hotels;

        assertEquals(expected, actual);
    }

    @Test
    void readRoom()
    {
       List<RoomDTO> rooms =  hotels.get(0).getRooms();

       int expected = 6;
       int actual = rooms.size();

        assertEquals(expected, actual);
    }


    @Test
    void create()
    {
        HotelDTO hotelDTO = new HotelDTO("Random Hotel", "Random sted", Hotel.HotelType.LUXURY);
        HotelDTO actual = hotelDAO.create(hotelDTO);

        assertNotNull(actual);
    }


    @Test
    void update()
    {
        HotelDTO hotelDTO1 = hotels.get(2);
        hotelDTO1.setHotelName("Verdens bedste hotel");

        hotelDAO.update(3, hotelDTO1);
        String expectedName = "Verdens bedste hotel";
        String actual = hotels.get(2).getHotelName();

        assertEquals(expectedName, actual);
    }


    @Test
    void delete()
    {
        HotelDTO hotelDTO = hotels.get(2);
        int id = hotelDTO.getId();

        hotelDAO.delete(id);

        EntityManager em = emf.createEntityManager();
        Hotel deletedHotel = em.find(Hotel.class, id);
        em.close();

        assertNull(deletedHotel);
    }
}