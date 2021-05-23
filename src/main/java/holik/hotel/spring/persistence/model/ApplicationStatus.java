package holik.hotel.spring.persistence.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Application_statuses")
public class ApplicationStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int statusId) {
        this.id = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static ApplicationStatus getStatusBooked() {
        ApplicationStatus statusBooked = new ApplicationStatus();
        statusBooked.setId(4);
        return statusBooked;
    }

    public static ApplicationStatus getStatusPaid() {
        ApplicationStatus statusPaid = new ApplicationStatus();
        statusPaid.setId(5);
        return statusPaid;
    }

    public static ApplicationStatus getStatusApproved() {
        ApplicationStatus statusApproved = new ApplicationStatus();
        statusApproved.setId(2);
        return statusApproved;
    }

    public static ApplicationStatus getStatusDeclined() {
        ApplicationStatus statusDeclined = new ApplicationStatus();
        statusDeclined.setId(3);
        return statusDeclined;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ApplicationStatus status = (ApplicationStatus) o;
        return id == status.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
