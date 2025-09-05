package com.courseacademy.courseacademyservice.service;

import java.util.List;
import com.courseacademy.courseacademyservice.dto.ModuleDTO;

public interface ModuleService {
    ModuleDTO create(ModuleDTO dto);
    ModuleDTO getById(Long id);
    List<ModuleDTO> getAll();
    ModuleDTO update(Long id, ModuleDTO dto);
    void delete(Long id);
}
