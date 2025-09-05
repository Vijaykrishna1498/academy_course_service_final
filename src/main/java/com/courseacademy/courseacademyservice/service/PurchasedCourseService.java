package com.courseacademy.courseacademyservice.service;

import java.util.List;
import com.courseacademy.courseacademyservice.dto.PurchasedCourseDTO;

public interface PurchasedCourseService {
    PurchasedCourseDTO create(PurchasedCourseDTO dto);
    PurchasedCourseDTO getById(Long id);
    List<PurchasedCourseDTO> getAll();
    PurchasedCourseDTO update(Long id, PurchasedCourseDTO dto);
    void delete(Long id);
}
