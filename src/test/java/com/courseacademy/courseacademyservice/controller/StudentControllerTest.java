package com.courseacademy.courseacademyservice.controller;

import com.courseacademy.courseacademyservice.dto.StudentDTO;
import com.courseacademy.courseacademyservice.service.StudentService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class StudentControllerTest {

    @Mock
    private StudentService service;

    @InjectMocks
    private StudentController controller;

    @Test
    void testCreateStudent() {
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(1L);
        dto.setStudentName("John Doe");

        Mockito.when(service.create(dto)).thenReturn(dto);

        ResponseEntity<StudentDTO> response = controller.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getStudentName());
        Mockito.verify(service).create(dto);
    }

    @Test
    void testGetStudentById() {
        Long id = 1L;
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(id);
        dto.setStudentName("Jane Doe");

        Mockito.when(service.getById(id)).thenReturn(dto);

        ResponseEntity<StudentDTO> response = controller.get(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jane Doe", response.getBody().getStudentName());
        Mockito.verify(service).getById(id);
    }

    @Test
    void testListStudents() {
        StudentDTO dto1 = new StudentDTO();
        dto1.setStudentId(1L);
        dto1.setStudentName("Alice");

        StudentDTO dto2 = new StudentDTO();
        dto2.setStudentId(2L);
        dto2.setStudentName("Bob");

        List<StudentDTO> mockList = List.of(dto1, dto2);

        Mockito.when(service.getAll()).thenReturn(mockList);

        ResponseEntity<List<StudentDTO>> response = controller.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getStudentName());
        Mockito.verify(service).getAll();
    }

    @Test
    void testUpdateStudent() {
        Long id = 1L;
        StudentDTO dto = new StudentDTO();
        dto.setStudentId(id);
        dto.setStudentName("Charlie");

        Mockito.when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<StudentDTO> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Charlie", response.getBody().getStudentName());
        Mockito.verify(service).update(id, dto);
    }

    @Test
    void testDeleteStudent() {
        Long id = 1L;

        Mockito.doNothing().when(service).delete(id);

        ResponseEntity<Void> response = controller.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(service).delete(id);
    }
}
