package com.courseacademy.courseacademyservice.services;

import com.courseacademy.courseacademyservice.dto.StudentDTO;
import com.courseacademy.courseacademyservice.entity.Student;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.StudentRepository;
import com.courseacademy.courseacademyservice.service.impl.StudentServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentServiceImpl studentService;

    private Student student;
    private StudentDTO studentDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        student = new Student();
        student.setStudentId(1L);
        student.setStudentName("John Doe");
        student.setStudentEmail("john@example.com");
        student.setStudentPassword("password");

        studentDTO = new StudentDTO();
        studentDTO.setStudentId(1L);
        studentDTO.setStudentName("John Doe");
        studentDTO.setStudentEmail("john@example.com");
        studentDTO.setStudentPassword("password");
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        StudentDTO result = studentService.create(studentDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getStudentName());
        verify(studentRepository, times(1)).save(any(Student.class));
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        StudentDTO result = studentService.getById(1L);

        assertNotNull(result);
        assertEquals("john@example.com", result.getStudentEmail());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.getById(1L));
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(student));

        List<StudentDTO> result = studentService.getAll();

        assertEquals(1, result.size());
        assertEquals("John Doe", result.get(0).getStudentName());
        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testUpdateStudent_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        studentDTO.setStudentName("Updated Name");
        StudentDTO result = studentService.update(1L, studentDTO);

        assertEquals("Updated Name", result.getStudentName());
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> studentService.update(1L, studentDTO));
        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteStudent_Found() {
        when(studentRepository.existsById(1L)).thenReturn(true);

        studentService.delete(1L);

        verify(studentRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteStudent_NotFound() {
        when(studentRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> studentService.delete(1L));
        verify(studentRepository, never()).deleteById(anyLong());
    }
}
