package com.users.yehor_ostroverkhov.repository;

import com.users.yehor_ostroverkhov.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findAllByBirthDateAfterAndBirthDateBefore(LocalDate fromDate, LocalDate toDate);

    List<UserEntity> findAllByBirthDate(LocalDate fromDate);
}
