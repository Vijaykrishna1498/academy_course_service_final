package com.courseacademy.courseacademyservice.service;

import java.util.List;
import com.courseacademy.courseacademyservice.dto.CourseDTO;

public interface CourseService {
    CourseDTO create(CourseDTO dto);
    CourseDTO getById(Long id);
    List<CourseDTO> getAll();
    CourseDTO update(Long id, CourseDTO dto);
    void delete(Long id);
}
