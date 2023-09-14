package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface StudentTestsRepo extends JpaRepository<StudentTestEntity, UserTestId> {

    Optional<Set<StudentTestEntity>> findAllByTest(TestsEntity testsEntity);
    Optional<Set<StudentTestEntity>> findAllByUser(UserEntity testsEntity);
    Optional<StudentTestEntity> findByUserAndTest(UserEntity user, TestsEntity test);

}
