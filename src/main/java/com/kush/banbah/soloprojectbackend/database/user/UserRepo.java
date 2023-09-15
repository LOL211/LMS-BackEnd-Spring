package com.kush.banbah.soloprojectbackend.database.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);

    Optional<List<UserEntity>> findAllByRole(UserEntity.Role role);

    @Query(value = "SELECT uc.class_name FROM user_classes uc where user_id=:user_id", nativeQuery = true)
    Optional<List<String>> findClassNamesByUser_id(@Param("user_id") int u_id);

}
