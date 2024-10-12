package com.springrest.springrest.rest;

import com.springrest.springrest.entity.Student;
import jakarta.annotation.PostConstruct;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StudentRestController {
    private List<Student> theStudents;
    @PostConstruct
    public void loadData() {
        theStudents = new ArrayList<>();
        theStudents.add(new Student("John", "Patel", "Group1"));
        theStudents.add(new Student("Mario", "Rossi", "Group1"));
        theStudents.add(new Student("Mary", "Smith", "Group2"));
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return theStudents;
    }

    @PostMapping("/students")
    public Student addStudent(@RequestBody Student theStudent) {
        theStudents.add(theStudent);
        return theStudent;
    }

    //this method is part of homework of the Java Enterprise course
    @GetMapping("/students/group/{groupName}")
    public List<Student> getStudentsOfGroup(@PathVariable String groupName) {
        List<Student> studentsOfGroup = new ArrayList<>();
        for (Student student : theStudents) {
            if (student.getGroupName().equals(groupName)) {
                studentsOfGroup.add(student);
            }
        }
        return studentsOfGroup;
    }

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        if ( (studentId >= theStudents.size()) || (studentId < 0)) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }
        return theStudents.get(studentId);
    }


}