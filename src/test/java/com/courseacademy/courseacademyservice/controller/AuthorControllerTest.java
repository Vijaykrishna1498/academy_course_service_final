package com.courseacademy.courseacademyservice.controller;

import com.courseacademy.courseacademyservice.dto.AuthorDTO;
import com.courseacademy.courseacademyservice.service.AuthorService;
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
class AuthorControllerTest {

    @Mock
    private AuthorService service;

    @InjectMocks
    private AuthorController controller;

    @Test
    void testCreateAuthor() {
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorId(1L);
        dto.setAuthorName("John Doe");

        Mockito.when(service.create(dto)).thenReturn(dto);

        ResponseEntity<AuthorDTO> response = controller.create(dto);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("John Doe", response.getBody().getAuthorName());
        Mockito.verify(service).create(dto);
    }

    @Test
    void testGetAuthorById() {
        Long id = 1L;
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorId(id);
        dto.setAuthorName("Jane Doe");

        Mockito.when(service.getById(id)).thenReturn(dto);

        ResponseEntity<AuthorDTO> response = controller.get(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Jane Doe", response.getBody().getAuthorName());
        Mockito.verify(service).getById(id);
    }

    @Test
    void testListAuthors() {
        AuthorDTO dto1 = new AuthorDTO();
        dto1.setAuthorId(1L);
        dto1.setAuthorName("Author One");

        AuthorDTO dto2 = new AuthorDTO();
        dto2.setAuthorId(2L);
        dto2.setAuthorName("Author Two");

        List<AuthorDTO> mockList = List.of(dto1, dto2);

        Mockito.when(service.getAll()).thenReturn(mockList);

        ResponseEntity<List<AuthorDTO>> response = controller.list();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        assertEquals("Author One", response.getBody().get(0).getAuthorName());
        Mockito.verify(service).getAll();
    }

    @Test
    void testUpdateAuthor() {
        Long id = 1L;
        AuthorDTO dto = new AuthorDTO();
        dto.setAuthorId(id);
        dto.setAuthorName("Updated Name");

        Mockito.when(service.update(id, dto)).thenReturn(dto);

        ResponseEntity<AuthorDTO> response = controller.update(id, dto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Updated Name", response.getBody().getAuthorName());
        Mockito.verify(service).update(id, dto);
    }

    @Test
    void testDeleteAuthor() {
        Long id = 1L;

        Mockito.doNothing().when(service).delete(id);

        ResponseEntity<Void> response = controller.delete(id);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
        Mockito.verify(service).delete(id);
    }
}
