package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentTestsRepo extends JpaRepository<StudentTestEntity, UserTestId> {

    Optional<List<StudentTestEntity>> findAllByTest(TestsEntity testsEntity);

    Optional<List<StudentTestEntity>> findAllByUser(UserEntity testsEntity);

    Optional<StudentTestEntity> findByUserAndTest(UserEntity user, TestsEntity test);

}
