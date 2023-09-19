package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentTestsRepo extends JpaRepository<StudentTestEntity, UserTestId> {

    Optional<List<StudentTestEntity>> findAllByTest(TestsEntity testsEntity);

    Optional<List<StudentTestEntity>> findAllByUser(UserEntity testsEntity);

    Optional<StudentTestEntity> findByUserAndTest(UserEntity user, TestsEntity test);

    @Query(value = "SELECT st FROM StudentTestEntity as st where st.user=:user AND st.test in :tests" )
    Optional<List<StudentTestEntity>> findAllByUserAndTest(@Param("user") UserEntity user,@Param("tests") List<TestsEntity> test);

}
