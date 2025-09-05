package com.courseacademy.courseacademyservice.services;

import com.courseacademy.courseacademyservice.dto.PurchasedCourseDTO;
import com.courseacademy.courseacademyservice.entity.Course;
import com.courseacademy.courseacademyservice.entity.PurchasedCourse;
import com.courseacademy.courseacademyservice.entity.Student;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.CourseRepository;
import com.courseacademy.courseacademyservice.repository.PurchasedCourseRepository;
import com.courseacademy.courseacademyservice.repository.StudentRepository;
import com.courseacademy.courseacademyservice.service.impl.PurchasedCourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PurchasedCourseServiceImplTest {

    @Mock
    private PurchasedCourseRepository purchasedCourseRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private PurchasedCourseServiceImpl purchasedCourseService;

    private Student student;
    private Course course;
    private PurchasedCourse purchasedCourse;
    private PurchasedCourseDTO dto;

    @BeforeEach
    void setUp() {
        student = new Student();
        student.setStudentId(1L);

        course = new Course();
        course.setCourseId(10L);

        purchasedCourse = new PurchasedCourse();
        purchasedCourse.setPurchaseId(100L);
        purchasedCourse.setStudent(student);
        purchasedCourse.setCourse(course);
        purchasedCourse.setPurchaseDate(LocalDate.now());
        purchasedCourse.setAmountPaid(BigDecimal.valueOf(500.0));
        purchasedCourse.setStatus("ACTIVE");

        dto = new PurchasedCourseDTO();
        dto.setPurchaseId(100L);
        dto.setStudentId(1L);
        dto.setCourseId(10L);
        dto.setPurchaseDate(LocalDate.now());
        dto.setAmountPaid(BigDecimal.valueOf(500.0));
        dto.setStatus("ACTIVE");
    }

    @Test
    void create_ShouldSaveAndReturnDTO() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));
        when(purchasedCourseRepository.save(any(PurchasedCourse.class))).thenReturn(purchasedCourse);

        PurchasedCourseDTO result = purchasedCourseService.create(dto);

        assertThat(result).isNotNull();
        assertThat(result.getPurchaseId()).isEqualTo(100L);
        verify(purchasedCourseRepository, times(1)).save(any(PurchasedCourse.class));
    }

    @Test
    void create_ShouldThrow_WhenStudentNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchasedCourseService.create(dto));
    }

    @Test
    void create_ShouldThrow_WhenCourseNotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchasedCourseService.create(dto));
    }


    @Test
    void getById_ShouldReturnDTO_WhenFound() {
        when(purchasedCourseRepository.findById(100L)).thenReturn(Optional.of(purchasedCourse));

        PurchasedCourseDTO result = purchasedCourseService.getById(100L);

        assertThat(result).isNotNull();
        assertThat(result.getPurchaseId()).isEqualTo(100L);
    }

    @Test
    void getById_ShouldThrow_WhenNotFound() {
        when(purchasedCourseRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchasedCourseService.getById(100L));
    }


    @Test
    void getAll_ShouldReturnListOfDTOs() {
        when(purchasedCourseRepository.findAll()).thenReturn(Arrays.asList(purchasedCourse));

        List<PurchasedCourseDTO> result = purchasedCourseService.getAll();

        assertThat(result).hasSize(1);
        assertThat(result.get(0).getPurchaseId()).isEqualTo(100L);
    }


    @Test
    void update_ShouldModifyAndReturnDTO() {
        when(purchasedCourseRepository.findById(100L)).thenReturn(Optional.of(purchasedCourse));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(10L)).thenReturn(Optional.of(course));

        PurchasedCourseDTO result = purchasedCourseService.update(100L, dto);

        assertThat(result).isNotNull();
        assertThat(result.getStatus()).isEqualTo("ACTIVE");
    }

    @Test
    void update_ShouldThrow_WhenPurchaseNotFound() {
        when(purchasedCourseRepository.findById(100L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchasedCourseService.update(100L, dto));
    }

    @Test
    void update_ShouldThrow_WhenStudentNotFound() {
        when(purchasedCourseRepository.findById(100L)).thenReturn(Optional.of(purchasedCourse));
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchasedCourseService.update(100L, dto));
    }

    @Test
    void update_ShouldThrow_WhenCourseNotFound() {
        when(purchasedCourseRepository.findById(100L)).thenReturn(Optional.of(purchasedCourse));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));
        when(courseRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> purchasedCourseService.update(100L, dto));
    }


    @Test
    void delete_ShouldRemove_WhenExists() {
        when(purchasedCourseRepository.existsById(100L)).thenReturn(true);

        purchasedCourseService.delete(100L);

        verify(purchasedCourseRepository, times(1)).deleteById(100L);
    }

    @Test
    void delete_ShouldThrow_WhenNotFound() {
        when(purchasedCourseRepository.existsById(100L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> purchasedCourseService.delete(100L));
    }
}