package holik.hotel.spring.persistence.model;

import javax.persistence.*;
import java.util.Objects;

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

    public static RoomStatus getStatusUnavailable() {
        RoomStatus statusUnavailable = new RoomStatus();
        statusUnavailable.setId(4);
        return statusUnavailable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomStatus that = (RoomStatus) o;
        return statusId == that.statusId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(statusId);
    }
}
