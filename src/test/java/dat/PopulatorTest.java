package dat;

import dat.daos.impl.HotelDAO;
import dat.dtos.HotelDTO;
import dat.dtos.RoomDTO;
import dat.entities.Hotel;
import dat.entities.RoomType;
import jakarta.persistence.EntityManagerFactory;

import java.util.ArrayList;
import java.util.List;

public class PopulatorTest
{

    private static EntityManagerFactory emf;
    private static HotelDAO hotelDAO;

    public PopulatorTest(HotelDAO hotelDAO, EntityManagerFactory emf)
    {
        this.hotelDAO = hotelDAO;
        this.emf = emf;

    }

    public List<HotelDTO> populateHotels()
    {
        HotelDTO h1, h2, h3;

        h1 = new HotelDTO("Hotel California", "California", Hotel.HotelType.LUXURY);
        h2 = new HotelDTO("Hilton", "Copenhagen", Hotel.HotelType.STANDARD);
        h3 = new HotelDTO("Det bedste hotel", "København", Hotel.HotelType.BUDGET);

        h1 = hotelDAO.create(h1);
        h2 = hotelDAO.create(h2);
        h3 = hotelDAO.create(h3);

        List<RoomDTO> callCaliforniaRooms = callCaliforniaRooms();
        List<RoomDTO> callHiltonRooms = callHiltonRooms();
        List<RoomDTO> callBedsteHotelRooms = callBedsteHotelRooms();

       h1.setRooms(callCaliforniaRooms);
       h2.setRooms(callHiltonRooms);
       h3.setRooms(callBedsteHotelRooms);

        return new ArrayList<>(List.of(h1, h2,h3));
    }

    public static List<RoomDTO> callCaliforniaRooms()
    {
        List<RoomDTO> rooms = new ArrayList<>();

        RoomDTO r100 = new RoomDTO(100, 2520, RoomType.SINGLE);
        RoomDTO r101 = new RoomDTO(101, 2520, RoomType.SINGLE);
        RoomDTO r102 = new RoomDTO(102, 2520, RoomType.SINGLE);
        RoomDTO r103 = new RoomDTO(103, 2520, RoomType.SINGLE);
        RoomDTO r104 = new RoomDTO(104, 2520, RoomType.DOUBLE);
        RoomDTO r105 = new RoomDTO(105, 2520, RoomType.SUITE);

        rooms.add(r100);
        rooms.add(r101);
        rooms.add(r102);
        rooms.add(r103);
        rooms.add(r104);
        rooms.add(r105);

        return rooms;
    }

    public static List<RoomDTO> callHiltonRooms()
    {
        List<RoomDTO> rooms = new ArrayList<>();

        RoomDTO r111 = new RoomDTO(111, 2520, RoomType.SINGLE);
        RoomDTO r112 = new RoomDTO(112, 2520, RoomType.SINGLE);
        RoomDTO r113 = new RoomDTO(113, 2520, RoomType.SINGLE);
        RoomDTO r114 = new RoomDTO(114, 2520, RoomType.DOUBLE);
        RoomDTO r115 = new RoomDTO(115, 3200, RoomType.DOUBLE);
        RoomDTO r116 = new RoomDTO(116, 4500, RoomType.SUITE);

        rooms.add(r111);
        rooms.add(r112);
        rooms.add(r113);
        rooms.add(r114);
        rooms.add(r115);
        rooms.add(r116);

        return rooms;
    }

    public static List<RoomDTO> callBedsteHotelRooms()
    {
        List<RoomDTO> rooms = new ArrayList<>();

        RoomDTO r117 = new RoomDTO(117, 2520, RoomType.SINGLE);
        RoomDTO r118 = new RoomDTO(118, 2520, RoomType.SINGLE);
        RoomDTO r119 = new RoomDTO(119, 2520, RoomType.SINGLE);
        RoomDTO r120 = new RoomDTO(120, 2520, RoomType.DOUBLE);
        RoomDTO r121 = new RoomDTO(121, 3200, RoomType.DOUBLE);
        RoomDTO r122 = new RoomDTO(122, 4500, RoomType.SUITE);

        rooms.add(r117);
        rooms.add(r118);
        rooms.add(r119);
        rooms.add(r120);
        rooms.add(r121);
        rooms.add(r122);

        return rooms;
    }


    public void cleanUpHotels()
    {
        // Delete all data from database
        try (var em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Hotel").executeUpdate();
            em.createNativeQuery("ALTER SEQUENCE hotel_id_seq RESTART WITH 1").executeUpdate();
            em.getTransaction().commit();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

}
