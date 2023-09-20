package com.kush.banbah.soloprojectbackend.database;

import com.kush.banbah.soloprojectbackend.database.classes.Class;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTest;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTestsRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.Tests;
import com.kush.banbah.soloprojectbackend.database.studentTest.TestsRepo;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
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
            User teacher = User.builder()
                    .name("Teacher " + (c + 1))
                    .email("teacher" + (c + 1) + "@gmail.com")
                    .password(passwordEncoder.encode("test" + (c + 1)))
                    .role(User.Role.TEACHER)
                    .build();
            userRepo.save(teacher);

            Class classs = Class.builder()
                    .teacher(teacher)
                    .className("CS" + (1200 + (c + 1)))
                    .build();
            classRepo.save(classs);
            Set<Class> classSet = new HashSet<>();
            classSet.add(classs);
            teacher.setTeacher_classes(classSet);
            userRepo.save(teacher);
        }

        List<Class> allClasses = classRepo.findAll();
        for (int c = 1; c <= 20; c++) {
            User student = User.builder()
                    .name("Student " + c)
                    .email("student" + c + "@gmail.com")
                    .password(passwordEncoder.encode("test"+ c))
                    .role(User.Role.STUDENT)
                    .build();
            int classnum = new Random().nextInt(1, 5);
            Set<Class> studentClasses = new HashSet<>();
            while (studentClasses.size() != classnum)
                studentClasses.add(getRandomElement(allClasses));

            student.setStudent_classes(studentClasses);
            userRepo.save(student);
        }

        for (int c = 1; c <= 20; c++) {

            Tests test = Tests.builder()
                    .testName("Test " + c)
                    .className(getRandomElement(allClasses))
                    .build();
            testsRepo.save(test);
        }

        List<User> allUsers = userRepo.findAllByRole(User.Role.STUDENT);
        Random rand = new Random();
        allUsers.forEach(val -> {

            List<Class> classes = classRepo.findClassByStudents(val);
            List<Tests> tests = testsRepo.findAllByClassNameIn(classes);

            tests.forEach(test -> {
                StudentTest st = StudentTest.builder()
                        .test(test)
                        .student(val)
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
