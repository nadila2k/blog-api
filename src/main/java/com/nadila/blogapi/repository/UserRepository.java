package com.nadila.blogapi.repository;

import com.nadila.blogapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Users findByEmail(String email);

    boolean existsByEmail(String email);
}
