package com.courseacademy.courseacademyservice.controller;

import com.courseacademy.courseacademyservice.dto.CourseDTO;
import com.courseacademy.courseacademyservice.service.CourseService;
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
class CourseControllerTest {

    @Mock
    private CourseService service;

    @InjectMocks
    private CourseController controller;

    @Test
    void testCreateCourse() {
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(1L);
        dto.setCourseTitle("Java Basics");

        Mockito.when(service.create(dto)).thenReturn(dto);

        ResponseEntity<CourseDTO> response = controller.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Java Basics", response.getBody().getCourseTitle());
        Mockito.verify(service).create(dto);
    }

    @Test
    void testGetCourseById() {
        Long id = 1L;
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(id);
        dto.setCourseTitle("Spring Boot");

        Mockito.when(service.getById(id)).thenReturn(dto);

        ResponseEntity<CourseDTO> response = controller.get(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Spring Boot", response.getBody().getCourseTitle());
        Mockito.verify(service).getById(id);
    }

    @Test
    void testListCourses() {
        CourseDTO dto1 = new CourseDTO();
        dto1.setCourseId(1L);
        dto1.setCourseTitle("Java");

        CourseDTO dto2 = new CourseDTO();
        dto2.setCourseId(2L);
        dto2.setCourseTitle("Python");

        List<CourseDTO> mockList = List.of(dto1, dto2);

        Mockito.when(service.getAll()).thenReturn(mockList);

        ResponseEntity<List<CourseDTO>> response = controller.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Java", response.getBody().get(0).getCourseTitle());
        Mockito.verify(service).getAll();
    }


    @Test
    void testUpdateCourse() {
        Long id = 1L;
        CourseDTO dto = new CourseDTO();
        dto.setCourseId(id);
        dto.setCourseTitle("Advanced Java");

        Mockito.when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<CourseDTO> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Advanced Java", response.getBody().getCourseTitle());
        Mockito.verify(service).update(id, dto);
    }

    @Test
    void testDeleteCourse() {
        Long id = 1L;

        Mockito.doNothing().when(service).delete(id);

        ResponseEntity<Void> response = controller.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(service).delete(id);
    }
}
