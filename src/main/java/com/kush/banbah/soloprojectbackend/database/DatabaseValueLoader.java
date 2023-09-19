package com.kush.banbah.soloprojectbackend.database;

import com.kush.banbah.soloprojectbackend.database.classes.ClassEntity;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTestEntity;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTestsRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.TestsEntity;
import com.kush.banbah.soloprojectbackend.database.studentTest.TestsRepo;
import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

@Component
@AllArgsConstructor
public class DatabaseValueLoader implements ApplicationRunner {

    private final ClassRepo classRepo;
    private final StudentTestsRepo studentTestsRepo;
    private final TestsRepo testsRepo;
    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Override
    public void run(ApplicationArguments args) {

        for (int c = 0; c < 4; c++) {
            UserEntity teacher = UserEntity.builder()
                    .name("Teacher " + (c + 1))
                    .email("teacher" + (c + 1) + "@gmail.com")
                    .password(passwordEncoder.encode("test" + (c + 1)))
                    .role(UserEntity.Role.TEACHER)
                    .build();
            userRepo.save(teacher);

            ClassEntity classs = ClassEntity.builder()
                    .teacher(teacher)
                    .className("CS" + (1200 + (c + 1)))
                    .build();
            classRepo.save(classs);
            Set<ClassEntity> classEntitySet = new HashSet<>();
            classEntitySet.add(classs);
            teacher.setClasses(classEntitySet);
            userRepo.save(teacher);
        }

        List<ClassEntity> allClasses = classRepo.findAll();
        for (int c = 1; c <= 20; c++) {
            UserEntity student = UserEntity.builder()
                    .name("Student " + c)
                    .email("student" + c + "@gmail.com")
                    .password(passwordEncoder.encode("test"+ c))
                    .role(UserEntity.Role.STUDENT)
                    .build();
            int classnum = new Random().nextInt(1, 5);
            Set<ClassEntity> studentClasses = new HashSet<>();
            while (studentClasses.size() != classnum)
                studentClasses.add(getRandomElement(allClasses));

            student.setClasses(studentClasses);
            userRepo.save(student);
        }

        for (int c = 1; c <= 20; c++) {

            TestsEntity test = TestsEntity.builder()
                    .test_name("Test " + c)
                    .className(getRandomElement(allClasses))
                    .build();
            testsRepo.save(test);
        }

        List<UserEntity> allUsers = userRepo.findAllByRole(UserEntity.Role.STUDENT).orElse(null);
        Random rand = new Random();
        allUsers.forEach(val -> {

            List<String> classes = userRepo.findClassNamesByUser_id(val.getId()).orElse(null);
            List<ClassEntity> cl = classRepo.findByClassNameIn(classes).orElse(null);
            List<TestsEntity> tests = testsRepo.findAllByClassNameIn(cl).orElse(null);

            tests.forEach(test -> {
                StudentTestEntity st = StudentTestEntity.builder()
                        .test(test)
                        .user(val)
                        .score(rand.nextInt(1, 101))
                        .build();
                studentTestsRepo.save(st);
            });
        });

    }

    public <T> T getRandomElement(List<T> list) {
        int randomElement = new Random().nextInt(list.size());
        return list.get(randomElement);
    }
}
