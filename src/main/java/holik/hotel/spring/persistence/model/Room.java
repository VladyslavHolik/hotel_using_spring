package holik.hotel.spring.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "Rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String number;
    private int price;
    private int space;

    @ManyToOne
    @JoinColumn(name="room_class", nullable=false)
    private RoomClass roomClass;

    @ManyToOne
    @JoinColumn(name="room_status", nullable=false)
    private RoomStatus roomStatus;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getSpace() {
        return space;
    }

    public void setSpace(int space) {
        this.space = space;
    }

    public RoomClass getRoomClass() {
        return roomClass;
    }

    public void setRoomClass(RoomClass roomClass) {
        this.roomClass = roomClass;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public String getPreview() {
        return "/images/rooms/" + id + "/1.jpg";
    }
}
