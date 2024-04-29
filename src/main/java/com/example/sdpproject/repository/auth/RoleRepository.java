package com.example.sdpproject.repository.auth;

import com.example.sdpproject.entity.enums.RoleEnum;
import com.example.sdpproject.entity.user.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findRoleByRole(RoleEnum role);
}
