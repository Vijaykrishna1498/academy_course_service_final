package com.courseacademy.courseacademyservice.service;

import java.util.List;
import com.courseacademy.courseacademyservice.dto.StudentDTO;

public interface StudentService {
    StudentDTO create(StudentDTO dto);
    StudentDTO getById(Long id);
    List<StudentDTO> getAll();
    StudentDTO update(Long id, StudentDTO dto);
    void delete(Long id);
}
