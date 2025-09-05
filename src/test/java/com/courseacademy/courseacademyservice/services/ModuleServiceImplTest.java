package com.courseacademy.courseacademyservice.services;
import com.courseacademy.courseacademyservice.dto.ModuleDTO;
import com.courseacademy.courseacademyservice.entity.Course;
import com.courseacademy.courseacademyservice.entity.Module;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.CourseRepository;
import com.courseacademy.courseacademyservice.repository.ModuleRepository;
import com.courseacademy.courseacademyservice.service.impl.ModuleServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModuleServiceImplTest {

    @Mock
    private ModuleRepository moduleRepository;

    @Mock
    private CourseRepository courseRepository;

    @InjectMocks
    private ModuleServiceImpl moduleService;

    private Module module;
    private ModuleDTO moduleDTO;
    private Course course;

    @BeforeEach
    void setUp() {
        course = new Course();
        course.setCourseId(1L);
        course.setCourseTitle("Java Basics");

        module = new Module();
        module.setModuleId(10L);
        module.setModuleTitle("Intro Module");
        module.setModuleContent("Introduction to Java");
        module.setCourse(course);

        moduleDTO = new ModuleDTO();
        moduleDTO.setModuleId(10L);
        moduleDTO.setModuleTitle("Intro Module");
        moduleDTO.setModuleContent("Introduction to Java");
        moduleDTO.setCourseId(1L);
    }

    // CREATE
    @Test
    void testCreateModule_Success() {
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));
        when(moduleRepository.save(any(Module.class))).thenReturn(module);

        ModuleDTO result = moduleService.create(moduleDTO);

        assertNotNull(result);
        assertEquals("Intro Module", result.getModuleTitle());
        verify(courseRepository, times(1)).findById(1L);
        verify(moduleRepository, times(1)).save(any(Module.class));
    }

    @Test
    void testCreateModule_CourseNotFound() {
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moduleService.create(moduleDTO));
        verify(moduleRepository, never()).save(any(Module.class));
    }

    // READ BY ID
    @Test
    void testGetModuleById_Success() {
        when(moduleRepository.findById(10L)).thenReturn(Optional.of(module));

        ModuleDTO result = moduleService.getById(10L);

        assertNotNull(result);
        assertEquals(10L, result.getModuleId());
        verify(moduleRepository, times(1)).findById(10L);
    }

    @Test
    void testGetModuleById_NotFound() {
        when(moduleRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moduleService.getById(10L));
    }

    // READ ALL
    @Test
    void testGetAllModules() {
        when(moduleRepository.findAll()).thenReturn(Arrays.asList(module));

        List<ModuleDTO> result = moduleService.getAll();

        assertEquals(1, result.size());
        assertEquals("Intro Module", result.get(0).getModuleTitle());
    }

    // UPDATE
    @Test
    void testUpdateModule_Success() {
        when(moduleRepository.findById(10L)).thenReturn(Optional.of(module));
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course));

        ModuleDTO result = moduleService.update(10L, moduleDTO);

        assertNotNull(result);
        assertEquals("Intro Module", result.getModuleTitle());
        verify(moduleRepository, times(1)).findById(10L);
        verify(courseRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateModule_NotFound() {
        when(moduleRepository.findById(10L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moduleService.update(10L, moduleDTO));
    }

    @Test
    void testUpdateModule_CourseNotFound() {
        when(moduleRepository.findById(10L)).thenReturn(Optional.of(module));
        when(courseRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> moduleService.update(10L, moduleDTO));
    }

    // DELETE
    @Test
    void testDeleteModule_Success() {
        when(moduleRepository.existsById(10L)).thenReturn(true);

        moduleService.delete(10L);

        verify(moduleRepository, times(1)).deleteById(10L);
    }

    @Test
    void testDeleteModule_NotFound() {
        when(moduleRepository.existsById(10L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> moduleService.delete(10L));
        verify(moduleRepository, never()).deleteById(10L);
    }
}
