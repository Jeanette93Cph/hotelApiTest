package dat.daos.impl;

import dat.daos.IDAO;
import dat.dtos.HotelDTO;
import dat.entities.Hotel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
public class HotelDAO implements IDAO<HotelDTO, Integer> {

    private static HotelDAO instance;
    private static EntityManagerFactory emf;

    public HotelDAO(EntityManagerFactory emf)
    {
        this.emf = emf;
    }


    public static HotelDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            emf = _emf;
            instance = new HotelDAO();
        }
        return instance;
    }


    @Override
    public HotelDTO read(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Hotel hotel = em.find(Hotel.class, integer);
            if(hotel == null)
            {
                return null;
            }
            return new HotelDTO(hotel);
        }
    }

    @Override
    public List<HotelDTO> readAll() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<HotelDTO> query = em.createQuery("SELECT new dat.dtos.HotelDTO(h) FROM Hotel h", HotelDTO.class);
            return query.getResultList();
        }
    }

    @Override
    public HotelDTO create(HotelDTO hotelDTO) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel = new Hotel(hotelDTO);
            em.persist(hotel);
            em.getTransaction().commit();
            return new HotelDTO(hotel);
        }
    }

    @Override
    public HotelDTO update(Integer integer, HotelDTO hotelDTO) {
        if(hotelDTO == null)
        {
            throw new IllegalArgumentException("HotelDTO cannot be null");
        }

        try (EntityManager em = emf.createEntityManager())
        {
            em.getTransaction().begin();
            Hotel h = em.find(Hotel.class, integer);
            h.setHotelName(hotelDTO.getHotelName());
            h.setHotelAddress(hotelDTO.getHotelAddress());
            h.setHotelType(hotelDTO.getHotelType());
            em.merge(h);
            em.getTransaction().commit();
            return new HotelDTO(h);
        }
    }

    @Override
    public void delete(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Hotel hotel = em.find(Hotel.class, integer);
            if (hotel != null) {
                em.remove(hotel);
            }
            em.getTransaction().commit();
        }
    }

    @Override
    public boolean validatePrimaryKey(Integer integer) {
        try (EntityManager em = emf.createEntityManager()) {
            Hotel hotel = em.find(Hotel.class, integer);
            return hotel != null;
        }
    }
}
