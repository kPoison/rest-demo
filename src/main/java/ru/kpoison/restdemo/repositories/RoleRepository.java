package ru.kpoison.restdemo.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.kpoison.restdemo.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
