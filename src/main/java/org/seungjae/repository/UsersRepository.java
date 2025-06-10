package org.seungjae.repository;

import org.seungjae.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users, String> {
    boolean existsByUsername(String username);
    boolean existsByEmail(String email);
    Optional<Users> findByEmail(String email);
}
