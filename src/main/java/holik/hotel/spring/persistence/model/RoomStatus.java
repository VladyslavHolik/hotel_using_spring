package holik.hotel.spring.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "Room_statuses")
public class RoomStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int statusId;
    private String statusName;

    public int getId() {
        return statusId;
    }

    public void setId(int statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return statusName;
    }

    public void setStatus(String statusName) {
        this.statusName = statusName;
    }

    public static RoomStatus getStatusFree() {
        RoomStatus statusFree = new RoomStatus();
        statusFree.setId(1);
        return statusFree;
    }

    public static RoomStatus getStatusBooked() {
        RoomStatus statusBooked = new RoomStatus();
        statusBooked.setId(2);
        return statusBooked;
    }

    public static RoomStatus getStatusBusy() {
        RoomStatus statusBusy = new RoomStatus();
        statusBusy.setId(3);
        return statusBusy;
    }
}
