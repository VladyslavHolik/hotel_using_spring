package holik.hotel.spring.persistence.model;

import javax.persistence.*;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RoomClass roomClass = (RoomClass) o;
        return classId == roomClass.classId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(classId);
    }
}
