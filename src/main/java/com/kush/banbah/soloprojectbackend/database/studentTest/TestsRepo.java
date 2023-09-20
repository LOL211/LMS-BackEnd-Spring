package com.kush.banbah.soloprojectbackend.database.studentTest;


import com.kush.banbah.soloprojectbackend.database.classes.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestsRepo extends JpaRepository<Tests, Integer> {
    List<Tests> findByBelongsToClass(Class aClass);
    List<Tests> findAllByBelongsToClassIn(List<Class> classList);

    Optional<Tests> findByTestName(String testName);
}
