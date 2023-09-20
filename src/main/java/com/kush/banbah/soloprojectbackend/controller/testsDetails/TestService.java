package com.kush.banbah.soloprojectbackend.controller.testsDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kush.banbah.soloprojectbackend.database.classes.Class;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTest;
import com.kush.banbah.soloprojectbackend.database.studentTest.StudentTestsRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.Tests;
import com.kush.banbah.soloprojectbackend.database.studentTest.TestsRepo;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import com.kush.banbah.soloprojectbackend.exceptions.*;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
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
        User user = (User) auth.getPrincipal();

        List<Class> classList = classRepo.findClassByStudents(user);

        Class aClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));

        if (!classList.contains(aClass))
            throw new UserDoesNotBelongToClassException("User " + user.getName() + " does not belong to " + className);


        List<Tests> tests = testsRepo.findByBelongsToClass(aClass);

        List<StudentTest> studentTestEntities = studentTestsRepo.findAllByStudentAndTest(user, tests);
        List<StudentTestResponse> responseMap = new ArrayList<>();

        studentTestEntities.forEach(val ->
                responseMap.add(new StudentTestResponse(val.getTest().getTestName(), val.getScore()))
        );
        ObjectMapper mapper = new ObjectMapper();

        return mapper.valueToTree(responseMap).toString();
    }

    public String getTeacherTests(String className, Authentication auth) throws ClassDoesNotExistException, NotTeacherOfClassException {

        User user = (User) auth.getPrincipal();


        Class aClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));

        if (aClass.getTeacher().getId() != user.getId())
            throw new NotTeacherOfClassException(user.getName() + " does not teach " + aClass.getClassName());

        List<Tests> tests = testsRepo.findByBelongsToClass(aClass);


        ObjectMapper mapper = new ObjectMapper();
        List<String> testNames = tests.stream().map(Tests::getTestName).toList();

        return mapper.valueToTree(testNames).toString();
    }

    public String getTeacherStudentScores(String className, String testName, Authentication auth) throws ClassDoesNotExistException, TestNotFoundException, TestDoesNotBelongToClassException, NotTeacherOfClassException {
        User user = (User) auth.getPrincipal();


        Class requestClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));
        if (requestClass.getTeacher().getId() != user.getId())
            throw new NotTeacherOfClassException(user.getName() + " does not teach " + requestClass.getClassName());


        Tests test = testsRepo.findByTestName(testName).orElseThrow(() -> new TestNotFoundException("Test of " + testName + " does not exist"));


        if (!test.getBelongsToClass().getClassName().equals(requestClass.getClassName()))
            throw new TestDoesNotBelongToClassException("Test " + testName + " does not belong to class " + className);

        List<Object[]> queryResponses = studentTestsRepo.findAllStudentTestByTest(className, test.getTest_id());


        List<TeacherTestResponse> responses = queryResponses.stream()
                .map(val -> new TeacherTestResponse(userRepo.findById((int) val[1]).orElseThrow(NullPointerException::new).getName(), (long) val[0]))
                .toList();


        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(responses).toString();


    }
}
