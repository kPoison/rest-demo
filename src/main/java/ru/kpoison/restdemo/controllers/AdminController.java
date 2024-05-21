package ru.kpoison.restdemo.controllers;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.kpoison.restdemo.models.User;
import ru.kpoison.restdemo.services.UserService;
import ru.kpoison.restdemo.util.UserErrorResponse;
import ru.kpoison.restdemo.util.UserException;
import ru.kpoison.restdemo.util.UserNotCreatedException;
import ru.kpoison.restdemo.dto.UserDTO;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

//TODO login
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private UserService userService;
    private ModelMapper modelMapper;

    public AdminController(UserService userService, ModelMapper modelMapper) {
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/get-users-table")
    public ResponseEntity<List<UserDTO>> getUsersTable() {
        return new ResponseEntity<>(
                userService.getAll().stream().map(this::convertToUserDTO).collect(Collectors.toList()),
                HttpStatus.OK
        );
    }

    @GetMapping("/get-user/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable long id) {
        return new ResponseEntity<>(convertToUserDTO(userService.getById(id)), HttpStatus.FOUND);
    }

    @PostMapping("/create-user")
    public ResponseEntity<HttpStatus> createUser(@RequestBody @Valid User user,
                                                 BindingResult bindingResult) {
        throwRequestError(bindingResult);

        if (user.getPassword().length() <= 3) {
            throw new UserNotCreatedException("password is too short");
        }

        userService.save(user);

        return ResponseEntity.ok(HttpStatus.CREATED);
    }

    @PatchMapping("/edit-user")
    public ResponseEntity<HttpStatus> editUser(@RequestBody @Valid User user,
                                               BindingResult bindingResult) {
        throwRequestError(bindingResult);

        if (user.getPassword().length() <= 3 && user.getPassword().length() > 0) {
            throw new UserNotCreatedException("password is too short");
        }

        if (user.getPassword().length() == 0) {
            user.setPassword(userService.getById(user.getId()).getPassword());
        }

        userService.save(user);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/delete-user")
    public ResponseEntity<HttpStatus> deleteUser(@RequestParam Long id) {
        userService.delete(id);

        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handleException(UserException e) {
        UserErrorResponse error = new UserErrorResponse(e.getMessage(), System.currentTimeMillis());

        return new ResponseEntity<>(error, e.getStatus());
    }

    private void throwRequestError(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuilder errors = new StringBuilder();

            for (FieldError error : bindingResult.getFieldErrors()) {
                errors.append(error.getField())
                        .append(": ")
                        .append(error.getDefaultMessage())
                        .append("; ");
            }

            throw new UserNotCreatedException(errors.toString());
        }
    }

    private User convertToUser(UserDTO userDTO) {
        return modelMapper.map(userDTO, User.class);
    }

    private UserDTO convertToUserDTO(User user) {
        return modelMapper.map(user, UserDTO.class);
    }
}
