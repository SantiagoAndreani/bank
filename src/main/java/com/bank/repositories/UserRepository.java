package com.bank.repositories;

import com.bank.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    boolean existsByDni(String dni);
    boolean existsByEmail(String email);

}
