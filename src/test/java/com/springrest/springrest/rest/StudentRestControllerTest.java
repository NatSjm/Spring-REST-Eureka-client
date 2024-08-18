package com.springrest.springrest.rest;

import com.springrest.springrest.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class StudentRestControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void getStudents_returnsAllStudents() throws Exception {
        mockMvc.perform(get("/api/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(3));
    }

    @Test
    void getStudentsOfGroup_withValidGroup_returnsStudents() throws Exception {
        mockMvc.perform(get("/api/students/group/Group1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void getStudentsOfGroup_withInvalidGroup_returnsEmptyList() throws Exception {
        mockMvc.perform(get("/api/students/group/Group3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void addStudent_createsNewStudent() throws Exception {
        Student newStudent = new Student("Jane", "Doe", "Group3");
        mockMvc.perform(post("/api/students")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"groupName\":\"Group3\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName").value("Jane"));
    }
}
