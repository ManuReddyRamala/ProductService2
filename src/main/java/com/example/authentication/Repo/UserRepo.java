package com.example.authentication.Repo;

import com.example.authentication.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User save(User user);//Upsert

    Optional<User> findByEmail(String email);
}
