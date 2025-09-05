package com.courseacademy.courseacademyservice.service.impl;

import com.courseacademy.courseacademyservice.dto.ModuleDTO;
import com.courseacademy.courseacademyservice.entity.Course;
import com.courseacademy.courseacademyservice.entity.Module;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.CourseRepository;
import com.courseacademy.courseacademyservice.repository.ModuleRepository;
import com.courseacademy.courseacademyservice.service.ModuleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final CourseRepository courseRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository, CourseRepository courseRepository) {
        this.moduleRepository = moduleRepository;
        this.courseRepository = courseRepository;
    }


    @Override
    public ModuleDTO create(ModuleDTO dto) {
        Course c = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + dto.getCourseId()));
        Module m = toEntity(dto, c);
        return toDTO(moduleRepository.save(m));
    }


    @Override
    public ModuleDTO getById(Long id) {
        Module m = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + id));
        return toDTO(m);
    }


    @Override
    public List<ModuleDTO> getAll() {
        return moduleRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public ModuleDTO update(Long id, ModuleDTO dto) {
        Module m = moduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found: " + id));

        Course c = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + dto.getCourseId()));

        updateEntity(m, dto, c);
        return toDTO(m);
    }


    @Override
    public void delete(Long id) {
        if (!moduleRepository.existsById(id)) {
            throw new ResourceNotFoundException("Module not found: " + id);
        }
        moduleRepository.deleteById(id);
    }


    private ModuleDTO toDTO(Module m) {
        if (m == null) return null;

        ModuleDTO d = new ModuleDTO();
        d.setModuleId(m.getModuleId());
        d.setModuleTitle(m.getModuleTitle());
        d.setModuleContent(m.getModuleContent());
        d.setCourseId(m.getCourse() != null ? m.getCourse().getCourseId() : null);
        return d;
    }

    private Module toEntity(ModuleDTO d, Course c) {
        if (d == null) return null;

        Module m = new Module();
        m.setModuleId(d.getModuleId());
        m.setModuleTitle(d.getModuleTitle());
        m.setModuleContent(d.getModuleContent());
        m.setCourse(c);
        return m;
    }

    private void updateEntity(Module m, ModuleDTO d, Course c) {
        m.setModuleTitle(d.getModuleTitle());
        m.setModuleContent(d.getModuleContent());
        m.setCourse(c);
    }
}
