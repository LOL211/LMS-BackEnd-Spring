package com.kush.banbah.soloprojectbackend.controller.testsDetails;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest.ScoreUpdateRequest;
import com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest.StudentTestResponse;
import com.kush.banbah.soloprojectbackend.controller.testsDetails.ResponseAndRequest.TeacherTestResponse;
import com.kush.banbah.soloprojectbackend.database.classes.Class;
import com.kush.banbah.soloprojectbackend.database.classes.ClassRepo;
import com.kush.banbah.soloprojectbackend.database.studentTest.*;
import com.kush.banbah.soloprojectbackend.database.user.User;
import com.kush.banbah.soloprojectbackend.database.user.UserRepo;
import com.kush.banbah.soloprojectbackend.exceptions.*;
import com.kush.banbah.soloprojectbackend.exceptions.entityDoesNotBelongToClass.TestDoesNotBelongToClassException;
import com.kush.banbah.soloprojectbackend.exceptions.entityDoesNotBelongToClass.UserDoesNotBelongToClassException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.ClassDoesNotExistException;
import com.kush.banbah.soloprojectbackend.exceptions.entityNotFound.TestNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
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

    public String getStudentScores(String className, Authentication auth) throws EntityDoesNotBelongException, EntityNotFoundException {
        User user = (User) auth.getPrincipal();

        List<Class> classList = classRepo.findClassByStudents(user);

        Class aClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));

        if (!classList.contains(aClass))
            throw new UserDoesNotBelongToClassException("Student " + user.getName() + " does not belong to " + className);


        List<Tests> tests = testsRepo.findByBelongsToClass(aClass);

        List<StudentTest> studentTestEntities = studentTestsRepo.findAllByStudentAndTests(user, tests);
        List<StudentTestResponse> responseMap = new ArrayList<>();

        studentTestEntities.forEach(val ->
                responseMap.add(new StudentTestResponse(val.getTest().getTestName(), val.getScore()))
        );
        ObjectMapper mapper = new ObjectMapper();

        return mapper.valueToTree(responseMap).toString();
    }

    public String getTeacherTests(String className, Authentication auth) throws EntityDoesNotBelongException, EntityNotFoundException {

        User user = (User) auth.getPrincipal();


        Class aClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));

        if (aClass.getTeacher().getId() != user.getId())
            throw new UserDoesNotBelongToClassException(user.getName() + " does not teach " + aClass.getClassName());

        List<Tests> tests = testsRepo.findByBelongsToClass(aClass);


        ObjectMapper mapper = new ObjectMapper();
        List<String> testNames = tests.stream().map(Tests::getTestName).toList();

        return mapper.valueToTree(testNames).toString();
    }

    public String getTeacherStudentScores(String className, String testName, Authentication auth) throws EntityDoesNotBelongException, EntityNotFoundException {
        User user = (User) auth.getPrincipal();


        Class requestClass = classRepo.findByClassName(className).orElseThrow(() -> new ClassDoesNotExistException(className));
        if (requestClass.getTeacher().getId()!=user.getId())
            throw new UserDoesNotBelongToClassException(user.getName() + " does not teach " + requestClass.getClassName());


        Tests test = testsRepo.findByTestName(testName).orElseThrow(() -> new TestNotFoundException("Test of " + testName + " does not exist"));


        if (!test.getBelongsToClass().getClassName().equals(requestClass.getClassName()))
            throw new TestDoesNotBelongToClassException("Test " + testName + " does not belong to class " + className);

        List<Object[]> queryResponses = studentTestsRepo.findAllStudentTestByTest(className, test.getTest_id());


        List<TeacherTestResponse> responses = queryResponses.stream()
                .map(val -> new TeacherTestResponse(userRepo.findById((int) val[1]).orElseThrow(NullPointerException::new).getName(), (long) val[0], (int) val[1]))
                .toList();


        ObjectMapper mapper = new ObjectMapper();
        return mapper.valueToTree(responses).toString();


    }

    public User updateGrade(String className, String testName,int studentID, Authentication auth, ScoreUpdateRequest newScore) throws EntityDoesNotBelongException, EntityNotFoundException {

        User teacher = (User) auth.getPrincipal();

        User student = userRepo.findById(studentID).orElseThrow(()->new UsernameNotFoundException("Student of ID "+studentID+" does not exist"));

        Class requestClass = classRepo.findByClassName(className).orElseThrow(()->new ClassDoesNotExistException(className));

        Tests test = testsRepo.findByTestName(testName).orElseThrow(() -> new TestNotFoundException("Test of " + testName + " does not exist"));

        List<Class> classList = classRepo.findClassByStudents(student);

        if (requestClass.getTeacher().getId()!=teacher.getId())
            throw new UserDoesNotBelongToClassException(teacher.getName() + " does not teach " + requestClass.getClassName());

        if (!test.getBelongsToClass().getClassName().equals(requestClass.getClassName()))
            throw new TestDoesNotBelongToClassException("Test " + testName + " does not belong to class " + className);

        if (!classList.contains(requestClass))
            throw new UserDoesNotBelongToClassException("Student " + student.getName() + " does not belong to " + className);

        StudentTest updateTest = studentTestsRepo.findByStudentAndTest(student,test).orElse(StudentTest.builder().student(student).test(test).score(0).build());

        updateTest.setScore(Integer.parseInt(newScore.getNewScore()));

        studentTestsRepo.saveAndFlush(updateTest);
        return student;
    }

    public void createTest(String className, Authentication auth, String newTestName) throws EntityDoesNotBelongException, EntityNotFoundException, InvalidTestNameException {
        User teacher = (User) auth.getPrincipal();

        Class requestClass = classRepo.findByClassName(className).orElseThrow(()->new ClassDoesNotExistException(className));

        if (requestClass.getTeacher().getId()!=teacher.getId())
            throw new UserDoesNotBelongToClassException(teacher.getName() + " does not teach " + requestClass.getClassName());
            try{
                Tests addTest = Tests.builder()
                        .testName(newTestName)
                        .belongsToClass(requestClass)
                        .build();
                testsRepo.saveAndFlush(addTest);
            }catch(DataIntegrityViolationException e)
            {
                throw new InvalidTestNameException("Test "+newTestName+" already exists in class "+className);
            }



    }
}
