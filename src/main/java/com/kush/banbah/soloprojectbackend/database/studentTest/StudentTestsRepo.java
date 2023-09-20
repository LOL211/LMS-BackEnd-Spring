package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentTestsRepo extends JpaRepository<StudentTest, UserTestId> {



    @Query(value = "SELECT st FROM StudentTest as st where st.student=:student AND st.test in :tests" )
    List<StudentTest> findAllByStudentAndTest(@Param("student") User student, @Param("tests") List<Tests> test);

//    @Query(value =
//            "SELECT score, u.user_id " +
//            "FROM user_test st" +
//            "RIGHT JOIN :students u" +
//            "ON u.user_id=st.user_id AND st.test_id=:test_id", nativeQuery = true)
//    Optional<List<String>> findAllStudentTestByTest(@Param("students") int[] students, @Param("test_id") int test_id);
}
