package holik.hotel.spring.persistence.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "Applications")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int userId;
    private int space;
    private int roomId;

    @ManyToOne
    @JoinColumn(name="room_class", nullable=false)
    private RoomClass roomClass;
    @ManyToOne
    @JoinColumn(name="status", nullable=false)
    private ApplicationStatus status;

    private LocalDateTime arrival;
    private LocalDateTime leaving;
    private LocalDateTime booked;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime datetimeOfArrival) {
        this.arrival = datetimeOfArrival;
    }

    public LocalDateTime getLeaving() {
        return leaving;
    }

    public void setLeaving(LocalDateTime datetimeOfLeaving) {
        this.leaving = datetimeOfLeaving;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }

    public LocalDateTime getBooked() {
        return booked;
    }

    public void setBooked(LocalDateTime datetimeOfBooking) {
        this.booked = datetimeOfBooking;
    }
}
