package com.kush.banbah.soloprojectbackend.database.studentTest;

import com.kush.banbah.soloprojectbackend.database.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentTestsRepo extends JpaRepository<StudentTest, UserTestId> {



    @Query(value = "SELECT st FROM StudentTest as st where st.student=:student AND st.test in :tests" )
    List<StudentTest> findAllByStudentAndTest(@Param("student") User student, @Param("tests") List<Tests> test);

    @Query(value =
            "SELECT  COALESCE(score, -1) as score, u.student_id " +
                    "FROM user_test st " +
                    "RIGHT JOIN (SELECT student_id from student_classes where class_name=:classname) u " +
                    "ON u.student_id=st.student_id AND test_id=:test_id", nativeQuery = true)
    List<Object[]> findAllStudentTestByTest(@Param("classname") String classname , @Param("test_id") int test_id);

}
