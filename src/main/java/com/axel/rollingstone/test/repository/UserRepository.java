package com.axel.rollingstone.test.repository;

import com.axel.rollingstone.test.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Integer> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByUsernameAndPassword(String username, String password);
}
