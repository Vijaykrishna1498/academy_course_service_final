package com.courseacademy.courseacademyservice.services;

import com.courseacademy.courseacademyservice.dto.AuthorDTO;
import com.courseacademy.courseacademyservice.entity.Author;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.AuthorRepository;
import com.courseacademy.courseacademyservice.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthorServiceImplTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;
    private AuthorDTO authorDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new Author();
        author.setAuthorId(1L);
        author.setAuthorName("John Doe");
        author.setAuthorPassword("secret");

        authorDTO = new AuthorDTO();
        authorDTO.setAuthorId(1L);
        authorDTO.setAuthorName("John Doe");
        authorDTO.setAuthorPassword("secret");
    }

    @Test
    void testCreate() {
        when(authorRepository.save(any(Author.class))).thenReturn(author);

        AuthorDTO result = authorService.create(authorDTO);

        assertNotNull(result);
        assertEquals("John Doe", result.getAuthorName());
        verify(authorRepository, times(1)).save(any(Author.class));
    }

    @Test
    void testGetById_Found() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO result = authorService.getById(1L);

        assertNotNull(result);
        assertEquals("John Doe", result.getAuthorName());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetById_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.getById(1L));
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAll() {
        Author another = new Author();
        another.setAuthorId(2L);
        another.setAuthorName("Jane Doe");
        another.setAuthorPassword("pwd");

        when(authorRepository.findAll()).thenReturn(Arrays.asList(author, another));

        List<AuthorDTO> result = authorService.getAll();

        assertEquals(2, result.size());
        verify(authorRepository, times(1)).findAll();
    }

    @Test
    void testUpdate_Found() {
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));

        AuthorDTO updateDTO = new AuthorDTO();
        updateDTO.setAuthorId(1L);
        updateDTO.setAuthorName("Updated Name");
        updateDTO.setAuthorPassword("newpass");

        AuthorDTO result = authorService.update(1L, updateDTO);

        assertNotNull(result);
        assertEquals("Updated Name", result.getAuthorName());
        assertEquals("newpass", result.getAuthorPassword());
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdate_NotFound() {
        when(authorRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> authorService.update(1L, authorDTO));
        verify(authorRepository, times(1)).findById(1L);
    }

    @Test
    void testDelete_Found() {
        when(authorRepository.existsById(1L)).thenReturn(true);

        authorService.delete(1L);

        verify(authorRepository, times(1)).existsById(1L);
        verify(authorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDelete_NotFound() {
        when(authorRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> authorService.delete(1L));
        verify(authorRepository, times(1)).existsById(1L);
        
    }
}
