package holik.hotel.spring.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "Room_classes")
public class RoomClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classId;
    private String className;

    public int getId() {
        return classId;
    }

    public void setId(int classId) {
        this.classId = classId;
    }

    public String getName() {
        return className;
    }

    public void setName(String className) {
        this.className = className;
    }
}
