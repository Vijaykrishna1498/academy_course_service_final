package com.courseacademy.courseacademyservice.service.impl;

import com.courseacademy.courseacademyservice.dto.CourseDTO;
import com.courseacademy.courseacademyservice.entity.Author;
import com.courseacademy.courseacademyservice.entity.Course;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.AuthorRepository;
import com.courseacademy.courseacademyservice.repository.CourseRepository;
import com.courseacademy.courseacademyservice.service.CourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final AuthorRepository authorRepository;

    public CourseServiceImpl(CourseRepository courseRepository, AuthorRepository authorRepository) {
        this.courseRepository = courseRepository;
        this.authorRepository = authorRepository;
    }


    @Override
    public CourseDTO create(CourseDTO dto) {
        Author author = dto.getAuthorId() != null
                ? authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + dto.getAuthorId()))
                : null;

        Course c = toEntity(dto, author);
        return toDTO(courseRepository.save(c));
    }


    @Override
    public CourseDTO getById(Long id) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));
        return toDTO(c);
    }


    @Override
    public List<CourseDTO> getAll() {
        return courseRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public CourseDTO update(Long id, CourseDTO dto) {
        Course c = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + id));

        Author author = dto.getAuthorId() != null
                ? authorRepository.findById(dto.getAuthorId())
                .orElseThrow(() -> new ResourceNotFoundException("Author not found: " + dto.getAuthorId()))
                : null;

        updateEntity(c, dto, author);
        return toDTO(c);
    }


    @Override
    public void delete(Long id) {
        if (!courseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Course not found: " + id);
        }
        courseRepository.deleteById(id);
    }


    private CourseDTO toDTO(Course c) {
        if (c == null) return null;

        CourseDTO d = new CourseDTO();
        d.setCourseId(c.getCourseId());
        d.setCourseTitle(c.getCourseTitle());
        d.setCourseDescription(c.getCourseDescription());
        d.setPrice(c.getPrice());
        d.setAuthorId(c.getAuthor() != null ? c.getAuthor().getAuthorId() : null);
        return d;
    }

    private Course toEntity(CourseDTO d, Author author) {
        if (d == null) return null;

        Course c = new Course();
        c.setCourseId(d.getCourseId());
        c.setCourseTitle(d.getCourseTitle());
        c.setCourseDescription(d.getCourseDescription());
        c.setPrice(d.getPrice());
        c.setAuthor(author);
        return c;
    }

    private void updateEntity(Course c, CourseDTO d, Author author) {
        c.setCourseTitle(d.getCourseTitle());
        c.setCourseDescription(d.getCourseDescription());
        c.setPrice(d.getPrice());
        c.setAuthor(author);
    }
}
