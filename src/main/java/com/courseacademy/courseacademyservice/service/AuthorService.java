package com.courseacademy.courseacademyservice.service;

import java.util.List;
import com.courseacademy.courseacademyservice.dto.AuthorDTO;

public interface AuthorService {
    AuthorDTO create(AuthorDTO dto);
    AuthorDTO getById(Long id);
    List<AuthorDTO> getAll();
    AuthorDTO update(Long id, AuthorDTO dto);
    void delete(Long id);
}
