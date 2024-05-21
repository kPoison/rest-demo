package ru.kpoison.restdemo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kpoison.restdemo.dto.UserDTO;
import ru.kpoison.restdemo.models.User;
import ru.kpoison.restdemo.services.UserService;

@RestController
@RequestMapping("/api")
public class UserController {
    private UserService userService;
    private ModelMapper modelMapper;

    public UserController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get-user-info")
    public ResponseEntity<UserDTO> getUserInfo() {
        return new ResponseEntity<>(convertToUserDTO(userService.getAuthUser()), HttpStatus.OK);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
