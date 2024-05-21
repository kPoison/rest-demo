package ru.kpoison.restdemo.services;

import org.springframework.stereotype.Service;
import ru.kpoison.restdemo.models.Role;
import ru.kpoison.restdemo.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> getAll() {
        return roleRepository.findAll();
    }
}
