package com.courseacademy.courseacademyservice.controller;

import com.courseacademy.courseacademyservice.dto.ModuleDTO;
import com.courseacademy.courseacademyservice.service.ModuleService;
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
class ModuleControllerTest {

    @Mock
    private ModuleService service;

    @InjectMocks
    private ModuleController controller;

    @Test
    void testCreateModule() {
        ModuleDTO dto = new ModuleDTO();
        dto.setModuleId(1L);
        dto.setModuleTitle("Introduction");

        Mockito.when(service.create(dto)).thenReturn(dto);

        ResponseEntity<ModuleDTO> response = controller.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Introduction", response.getBody().getModuleTitle());
        Mockito.verify(service).create(dto);
    }

    @Test
    void testGetModuleById() {
        Long id = 1L;
        ModuleDTO dto = new ModuleDTO();
        dto.setModuleId(id);
        dto.setModuleTitle("Spring Basics");

        Mockito.when(service.getById(id)).thenReturn(dto);

        ResponseEntity<ModuleDTO> response = controller.get(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Spring Basics", response.getBody().getModuleTitle());
        Mockito.verify(service).getById(id);
    }

    @Test
    void testListModules() {
        ModuleDTO dto1 = new ModuleDTO();
        dto1.setModuleId(1L);
        dto1.setModuleTitle("Java");

        ModuleDTO dto2 = new ModuleDTO();
        dto2.setModuleId(2L);
        dto2.setModuleTitle("Python");

        List<ModuleDTO> mockList = List.of(dto1, dto2);

        Mockito.when(service.getAll()).thenReturn(mockList);

        ResponseEntity<List<ModuleDTO>> response = controller.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Java", response.getBody().get(0).getModuleTitle());
        Mockito.verify(service).getAll();
    }

    @Test
    void testUpdateModule() {
        Long id = 1L;
        ModuleDTO dto = new ModuleDTO();
        dto.setModuleId(id);
        dto.setModuleTitle("Updated Module");

        Mockito.when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<ModuleDTO> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Module", response.getBody().getModuleTitle());
        Mockito.verify(service).update(id, dto);
    }

    @Test
    void testDeleteModule() {
        Long id = 1L;

        Mockito.doNothing().when(service).delete(id);

        ResponseEntity<Void> response = controller.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(service).delete(id);
    }
}
