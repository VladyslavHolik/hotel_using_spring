package holik.hotel.spring.web.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

public class UserDto {

    @NotEmpty(message = "First name is required")
    private String firstName;

    @NotEmpty(message = "Last name is required.")
    private String lastName;

    @NotEmpty(message = "Phone number is required.")
    private String phone;

    @Email
    @NotEmpty(message = "Email is required.")
    private String email;

    @NotEmpty(message = "Password is required.")
    private String password;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
