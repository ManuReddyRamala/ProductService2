package com.example.authentication.Repo;

import com.example.authentication.Models.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;
@Repository
public interface TokenRepo extends JpaRepository<Token, Long> {
   Optional<Token> findByValue(String token);
//    @Query(select * fr)
//    Optional<Token> findByValueAndIs_validAndExpiryGreaterThan(String token,boolean isValid,Date currentDate);
@Query("SELECT t FROM Token t WHERE t.value = :token AND t.is_valid = :isValid AND t.expiry > :currentDate")
Optional<Token> findByValueAndIs_validAndExpiryGreaterThan(@Param("token") String token,
                                                           @Param("isValid") boolean isValid, @Param("currentDate")
                                                           Date currentDate);
}
