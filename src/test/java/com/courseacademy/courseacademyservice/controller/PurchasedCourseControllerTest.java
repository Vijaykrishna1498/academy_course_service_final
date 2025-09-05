package com.courseacademy.courseacademyservice.controller;

import com.courseacademy.courseacademyservice.dto.PurchasedCourseDTO;
import com.courseacademy.courseacademyservice.service.PurchasedCourseService;
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
class PurchasedCourseControllerTest {

    @Mock
    private PurchasedCourseService service;

    @InjectMocks
    private PurchasedCourseController controller;

    @Test
    void testCreatePurchasedCourse() {
        PurchasedCourseDTO dto = new PurchasedCourseDTO();
        dto.setPurchaseId(1L);
        dto.setCourseId(101L);
        dto.setStudentId(501L);

        Mockito.when(service.create(dto)).thenReturn(dto);

        ResponseEntity<PurchasedCourseDTO> response = controller.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(101L, response.getBody().getCourseId());
        Mockito.verify(service).create(dto);
    }

    @Test
    void testGetPurchasedCourseById() {
        Long id = 1L;
        PurchasedCourseDTO dto = new PurchasedCourseDTO();
        dto.setPurchaseId(id);
        dto.setCourseId(202L);
        dto.setStudentId(602L);

        Mockito.when(service.getById(id)).thenReturn(dto);

        ResponseEntity<PurchasedCourseDTO> response = controller.get(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(202L, response.getBody().getCourseId());
        Mockito.verify(service).getById(id);
    }

    @Test
    void testListPurchasedCourses() {
        PurchasedCourseDTO dto1 = new PurchasedCourseDTO();
        dto1.setPurchaseId(1L);
        dto1.setCourseId(301L);
        dto1.setStudentId(701L);

        PurchasedCourseDTO dto2 = new PurchasedCourseDTO();
        dto2.setPurchaseId(2L);
        dto2.setCourseId(302L);
        dto2.setStudentId(702L);

        List<PurchasedCourseDTO> mockList = List.of(dto1, dto2);

        Mockito.when(service.getAll()).thenReturn(mockList);

        ResponseEntity<List<PurchasedCourseDTO>> response = controller.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals(301L, response.getBody().get(0).getCourseId());
        Mockito.verify(service).getAll();
    }

    @Test
    void testUpdatePurchasedCourse() {
        Long id = 1L;
        PurchasedCourseDTO dto = new PurchasedCourseDTO();
        dto.setPurchaseId(id);
        dto.setCourseId(401L);
        dto.setStudentId(801L);

        Mockito.when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<PurchasedCourseDTO> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(401L, response.getBody().getCourseId());
        Mockito.verify(service).update(id, dto);
    }

    @Test
    void testDeletePurchasedCourse() {
        Long id = 1L;

        Mockito.doNothing().when(service).delete(id);

        ResponseEntity<Void> response = controller.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(service).delete(id);
    }
}