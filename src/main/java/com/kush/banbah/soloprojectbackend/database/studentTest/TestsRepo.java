package com.kush.banbah.soloprojectbackend.database.studentTest;


import com.kush.banbah.soloprojectbackend.database.classes.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface TestsRepo extends JpaRepository<TestsEntity, Integer> {
    Optional<Set<TestsEntity>> findAllByClassName(ClassEntity classEntity);

}
