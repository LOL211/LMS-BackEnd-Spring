package com.kush.banbah.soloprojectbackend.controller.testsDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kush.banbah.soloprojectbackend.database.classes.ClassEntity;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTestEntity;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTestsRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.TestsEntity;
import com.kush.banbah.soloprojectbackend.database.studentTest.TestsRepo;
import com.kush.banbah.soloprojectbackend.database.user.UserEntity;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import com.kush.banbah.soloprojectbackend.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class TestService {

    private final TestsRepo testsRepo;

    private final StudentTestsRepo studentTestsRepo;
    private final UserRepo userRepo;

    private final ClassRepo classRepo;

    public String getStudentScores(String className, Authentication auth) throws UserDoesNotBelongToClassException, ClassDoesNotExistException {
        UserEntity user = (UserEntity) auth.getPrincipal();

        List<String> classList = userRepo.findClassNamesByUser_id(user.getId()).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        ClassEntity classEntity = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));

        if (!classList.contains(className))
            throw new UserDoesNotBelongToClassException("User " + user.getName() + " does not belong to " + className);


        List<TestsEntity> tests = testsRepo.findByClassName(classEntity).orElseThrow(NullPointerException::new);


        List<StudentTestEntity> studentTestEntities = studentTestsRepo.findAllByUserAndTest(user, tests).orElse(null);
        List<StudentTestResponse> responseMap = new ArrayList<>();

        studentTestEntities.forEach(val ->
                responseMap.add(new StudentTestResponse(val.getTest().getTest_name(), val.getScore()))
        );
        ObjectMapper mapper = new ObjectMapper();

        return mapper.valueToTree(responseMap).toString();
    }

    public String getTeacherTests(String className, Authentication auth) throws ClassDoesNotExistException, NotTeacherOfClassException {

        UserEntity user = (UserEntity) auth.getPrincipal();


        ClassEntity classEntity = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));

        if (classEntity.getTeacher().getId() != user.getId())
            throw new NotTeacherOfClassException(user.getName() + " does not teach " + classEntity.getClassName());

        List<TestsEntity> tests = testsRepo.findByClassName(classEntity).orElseThrow(NullPointerException::new);


        ObjectMapper mapper = new ObjectMapper();
        List<String> testNames = tests.stream().map(TestsEntity::getTest_name).toList();


        return mapper.valueToTree(testNames).toString();
    }

    public String getTeacherStudentScores(String className, int test_id, Authentication auth) throws ClassDoesNotExistException, TestNotFoundException, TestDoesNotBelongToClassException, NotTeacherOfClassException {
        UserEntity user = (UserEntity) auth.getPrincipal();


        ClassEntity classEntity = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));
        if (classEntity.getTeacher().getId() != user.getId())
            throw new NotTeacherOfClassException(user.getName() + " does not teach " + classEntity.getClassName());


        TestsEntity test = testsRepo.findById(test_id).orElseThrow(() -> new TestNotFoundException("Test of " + test_id + " does not exist"));

        if (!test.getClassName().equals(classEntity.getClassName()))
            throw new TestDoesNotBelongToClassException("Test " + test_id + " does not belong to class " + className);

        List<UserEntity> students = userRepo.findByClasses(classEntity).orElse(null);

        return "";
//        List<StudentTestEntity> testsTaken = studentTestsRepo.findAllByTest(test);


    }
}
