package com.kush.banbah.soloprojectbackend.database.classes;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepo extends JpaRepository<ClassEntity, String> {

    Optional<ClassEntity> findByClassName(String className);

    Optional<List<ClassEntity>> findByClassNameIn(List<String> classList);


}
