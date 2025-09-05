package com.courseacademy.courseacademyservice.service.impl;

import com.courseacademy.courseacademyservice.dto.AuthorDTO;
import com.courseacademy.courseacademyservice.entity.Author;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.AuthorRepository;
import com.courseacademy.courseacademyservice.service.AuthorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional  // roll back
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {

        this.authorRepository = authorRepository;
    }


    @Override
    public AuthorDTO create(AuthorDTO dto) {
        Author a = toEntity(dto);
        return toDTO(authorRepository.save(a));
    }


    @Override
    public AuthorDTO getById(Long id) {
        Author a = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + id));
        return toDTO(a);
    }


    @Override
    public List<AuthorDTO> getAll() {
        return authorRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public AuthorDTO update(Long id, AuthorDTO dto) {
        Author a = authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + id));
        updateEntity(a, dto);
        return toDTO(a);
    }


    @Override
    public void delete(Long id) {
        if (!authorRepository.existsById(id)) {
            throw new ResourceNotFoundException("Author not found: " + id);
        }
        authorRepository.deleteById(id);
    }

    private AuthorDTO toDTO(Author a) {
        if (a == null) return null;
        AuthorDTO d = new AuthorDTO();
        d.setAuthorId(a.getAuthorId());
        d.setAuthorName(a.getAuthorName());
        d.setAuthorPassword(a.getAuthorPassword());
        return d;
    }

    private Author toEntity(AuthorDTO d) {
        if (d == null) return null;
        Author a = new Author();
        a.setAuthorId(d.getAuthorId());
        a.setAuthorName(d.getAuthorName());
        a.setAuthorPassword(d.getAuthorPassword());
        return a;
    }

    private void updateEntity(Author a, AuthorDTO d) {
        a.setAuthorName(d.getAuthorName());
        a.setAuthorPassword(d.getAuthorPassword());
    }
}
