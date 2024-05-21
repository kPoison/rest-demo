package ru.kpoison.restdemo.dto;

import lombok.Data;
import ru.kpoison.restdemo.models.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Collection;

@Data
public class UserDTO {
    private Long id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 20, message = "2 to 20")
    private String username;

    @Email
    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotEmpty(message = "Roles should not be empty")
    private Collection<Role> roles;
}
