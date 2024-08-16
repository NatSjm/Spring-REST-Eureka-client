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

    @GetMapping("/students/{studentId}")
    public Student getStudent(@PathVariable int studentId) {
        return theStudents.get(studentId);
    }

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
    @PutMapping("/students/{studentId}")
    public Student updateStudent(@PathVariable int studentId, @RequestBody Student theStudent) {
        if (studentId >= theStudents.size() || studentId < 0) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }
        theStudents.set(studentId, theStudent);
        return theStudent;
    }

    @DeleteMapping("/students/{studentId}")
    public String deleteStudent(@PathVariable int studentId) {
        if (studentId >= theStudents.size() || studentId < 0) {
            throw new StudentNotFoundException("Student id not found - " + studentId);
        }
        theStudents.remove(studentId);
        return "Deleted student id - " + studentId;
    }
}
