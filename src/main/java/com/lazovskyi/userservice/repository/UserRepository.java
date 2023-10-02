package com.lazovskyi.userservice.repository;

import com.lazovskyi.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findAllByBirthdateBetween(LocalDate birthdate, LocalDate birthdate2);
}
