package com.example.messaging_api.repository;

import com.example.messaging_api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByPhoneNumber(String phoneNumber);
    User findByUserName(String userName);
}
