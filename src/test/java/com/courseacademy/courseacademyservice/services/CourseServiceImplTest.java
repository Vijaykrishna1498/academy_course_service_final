package com.courseacademy.courseacademyservice.services;

import com.courseacademy.courseacademyservice.dto.CourseDTO;
import com.courseacademy.courseacademyservice.entity.Author;
import com.courseacademy.courseacademyservice.entity.Course;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.AuthorRepository;
import com.courseacademy.courseacademyservice.repository.CourseRepository;
import com.courseacademy.courseacademyservice.service.impl.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private CourseServiceImpl courseService;

    private Course course;
    private CourseDTO courseDTO;
    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author();
        author.setAuthorId(1L);
        author.setAuthorName("John Doe");

        course = new Course();
        course.setCourseId(10L);
        course.setCourseTitle("Java Basics");
        course.setCourseDescription("Intro to Java");
        course.setPrice(new BigDecimal("299.99"));;
        course.setAuthor(author);

        courseDTO = new CourseDTO();
        courseDTO.setCourseId(10L);
        courseDTO.setCourseTitle("Java Basics");
        courseDTO.setCourseDescription("Intro to Java");
        courseDTO.setPrice(new BigDecimal("299.99"));
        courseDTO.setAuthorId(1L);
    }

    @Test
    void testCreateCourse_withValidAuthor() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        CourseDTO saved = courseService.create(courseDTO);

        assertNotNull(saved);
        assertEquals("Java Basics", saved.getCourseTitle());
        assertEquals(1L, saved.getAuthorId());
        verify(courseRepository, times(1)).save(any(Course.class));
    }

    @Test
    void testCreateCourse_authorNotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.create(courseDTO));
    }

    @Test
    void testGetById_found() {
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));

        CourseDTO found = courseService.getById(10L);

        assertNotNull(found);
        assertEquals("Java Basics", found.getCourseTitle());
    }

    @Test
    void testGetById_notFound() {
        when(courseRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.getById(99L));
    }

    @Test
    void testGetAll() {
        when(courseRepository.findAll()).thenReturn(Arrays.asList(course));

        List<CourseDTO> list = courseService.getAll();

        assertEquals(1, list.size());
        assertEquals("Java Basics", list.get(0).getCourseTitle());
    }

    @Test
    void testUpdateCourse_withValidAuthor() {
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        CourseDTO updated = new CourseDTO();
        updated.setCourseTitle("Advanced Java");
        updated.setCourseDescription("Deep dive into Java");
        updated.setPrice(new BigDecimal("299.99"));
        updated.setAuthorId(1L);

        CourseDTO result = courseService.update(10L, updated);

        assertEquals("Advanced Java", result.getCourseTitle());
        assertEquals("Deep dive into Java", result.getCourseDescription());
        assertEquals(BigDecimal.valueOf(299.99), result.getPrice());
        verify(courseRepository, never()).save(any(Course.class)); // since entity is updated in persistence context
    }

    @Test
    void testUpdateCourse_notFound() {
        when(courseRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> courseService.update(10L, courseDTO));
    }

    @Test
    void testDeleteCourse_found() {
        when(courseRepository.existsById(10L)).thenReturn(true);

        courseService.delete(10L);

        verify(courseRepository, times(1)).deleteById(10L);
    }

    @Test
    void testDeleteCourse_notFound() {
        when(courseRepository.existsById(10L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> courseService.delete(10L));
    }
}
