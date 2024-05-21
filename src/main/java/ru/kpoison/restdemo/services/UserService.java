package ru.kpoison.restdemo.services;


import ru.kpoison.restdemo.models.User;

import java.util.List;

public interface UserService {
    List<User> getAll();
    User getById(Long id);
    void save(User user);
    void delete(Long id);

    User getAuthUser();
}
