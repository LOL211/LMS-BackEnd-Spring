package com.kush.banbah.soloprojectbackend.database.classes;

import com.kush.banbah.soloprojectbackend.database.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ClassRepo extends JpaRepository<Class, String> {

    Optional<Class> findByClassName(String className);

    List<Class> findByClassNameIn(List<String> classList);

    List<Class> findClassByStudents(User student);

    List<Class> findClassByTeacher(User teacher);


}
