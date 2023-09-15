package com.kush.banbah.soloprojectbackend.database.studentTest;


import com.kush.banbah.soloprojectbackend.database.classes.ClassEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestsRepo extends JpaRepository<TestsEntity, Integer> {
    Optional<List<TestsEntity>> findByClassName(ClassEntity classEntity);

    Optional<List<TestsEntity>> findAllByClassNameIn(List<ClassEntity> classList);

}
