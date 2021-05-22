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
}
