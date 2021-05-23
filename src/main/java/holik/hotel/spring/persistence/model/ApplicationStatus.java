package holik.hotel.spring.persistence.model;

import javax.persistence.*;

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
}
