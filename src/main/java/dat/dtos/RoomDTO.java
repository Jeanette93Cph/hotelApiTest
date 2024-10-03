package dat.dtos;

import dat.entities.Room;
import dat.entities.RoomType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;


@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RoomDTO {


    private Integer Id;

    private Integer roomNumber;
    private Integer roomPrice;
    private RoomType roomType;


    public RoomDTO(Integer roomNumber, Integer roomPrice, RoomType roomType)
    {
        this.roomNumber = roomNumber;
        this.roomPrice = roomPrice;
        this.roomType = roomType;
    }

    public RoomDTO(Room room) {
        this.Id = room.getRoomId();
        this.roomNumber = room.getRoomNumber();
        this.roomPrice = room.getRoomPrice().intValue();
        this.roomType = room.getRoomType();
    }

    public RoomDTO(int roomNumber, BigDecimal bigDecimal, RoomType roomType)
    {
    }


    public static List<RoomDTO> toRoomDTOList(List<Room> rooms) {
        return List.of(rooms.stream().map(RoomDTO::new).toArray(RoomDTO[]::new));
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomDTO roomDTO = (RoomDTO) o;
        return Objects.equals(roomNumber, roomDTO.roomNumber) && Objects.equals(roomPrice, roomDTO.roomPrice) && roomType == roomDTO.roomType;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(roomNumber, roomPrice, roomType);
    }
}
