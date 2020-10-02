package com.bank.repositories;

import com.bank.entities.UserInfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfoEntity, Long> {

    Optional<UserInfoEntity> findById(Long id);

    boolean existsByCelPhone(Integer celPhone);
}
