package holik.hotel.spring.persistence.model;

import javax.persistence.*;

@Entity
@Table(name = "Roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roleId;
    private String roleName;

    public int getId() {
        return roleId;
    }

    public void setId(int id) {
        this.roleId = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
