package ProcureFlow.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateVendorRequest {

    @NotBlank(message = "Vendor name is required")
    @Size(
            min = 2,
            max = 150,
            message = "Vendor name must be between 2 and 150 characters"
    )
    private String name;

    @NotBlank(message = "Vendor email is required")
    @Email(message = "Vendor email must be valid")
    @Size(max = 150, message = "Vendor email must not exceed 150 characters")
    private String email;

    @NotBlank(message = "Vendor phone is required")
    @Size(
            max = 30,
            message = "Vendor phone must not exceed 30 characters"
    )
    private String phone;

    @Size(
            max = 500,
            message = "Vendor address must not exceed 500 characters"
    )
    private String address;

    public CreateVendorRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}