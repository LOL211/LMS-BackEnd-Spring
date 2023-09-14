package com.kush.banbah.soloprojectbackend;

import com.kush.banbah.soloprojectbackend.database.classes.ClassEntity;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTestsRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.TestsRepo;
import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@AllArgsConstructor
public class TestDatabase {

    private final ClassRepo classRepo;
    private final StudentTestsRepo studentTestsRepo;
    private final TestsRepo testsRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    public void runStuff(){

        for (int c = 0; c < 4; c++) {
            UserEntity teacher = UserEntity.builder()
                    .name("Teacher " + (c+1))
                    .email("teacher" + (c+1) + "@gmail.com")
                    .password(passwordEncoder.encode("test"+(c+1)))
                    .role(UserEntity.Role.TEACHER)
                    .build();
            userRepo.save(teacher);

            ClassEntity classs = ClassEntity.builder()
                    .teacher(teacher)
                    .className("CS" + (1200 + (c+1)))
                    .build();
            classRepo.save(classs);
        }

        List<ClassEntity> allClasses = classRepo.findAll();
        for (int c = 1; c <= 20; c++) {
            UserEntity student = UserEntity.builder()
                    .name("Student " + c)
                    .email("student" + c + "@gmail.com")
                    .password(passwordEncoder.encode("test")+c)
                    .role(UserEntity.Role.STUDENT)
                    .build();
            Set<ClassEntity> studentClasses = new HashSet<>(allClasses);
            student.setClasses(studentClasses);

            userRepo.save(student);
        }



    }

}
