package com.courseacademy.courseacademyservice.service.impl;

import com.courseacademy.courseacademyservice.dto.PurchasedCourseDTO;
import com.courseacademy.courseacademyservice.entity.Course;
import com.courseacademy.courseacademyservice.entity.PurchasedCourse;
import com.courseacademy.courseacademyservice.entity.Student;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.CourseRepository;
import com.courseacademy.courseacademyservice.repository.PurchasedCourseRepository;
import com.courseacademy.courseacademyservice.repository.StudentRepository;
import com.courseacademy.courseacademyservice.service.PurchasedCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class PurchasedCourseServiceImpl implements PurchasedCourseService {

    private final PurchasedCourseRepository purchasedCourseRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public PurchasedCourseServiceImpl(PurchasedCourseRepository purchasedCourseRepository,
                                      StudentRepository studentRepository,
                                      CourseRepository courseRepository) {
        this.purchasedCourseRepository = purchasedCourseRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public PurchasedCourseDTO create(PurchasedCourseDTO dto) {
        Student s = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + dto.getStudentId()));
        Course c = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + dto.getCourseId()));

        PurchasedCourse pc = toEntity(dto, s, c);
        return toDTO(purchasedCourseRepository.save(pc));
    }

    @Override
    public PurchasedCourseDTO getById(Long id) {
        PurchasedCourse pc = purchasedCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + id));
        return toDTO(pc);
    }


    @Override
    public List<PurchasedCourseDTO> getAll() {
        return purchasedCourseRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Override
    public PurchasedCourseDTO update(Long id, PurchasedCourseDTO dto) {
        PurchasedCourse pc = purchasedCourseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Purchase not found: " + id));

        Student s = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + dto.getStudentId()));
        Course c = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Course not found: " + dto.getCourseId()));

        updateEntity(pc, dto, s, c);
        return toDTO(pc);
    }


    @Override
    public void delete(Long id) {
        if (!purchasedCourseRepository.existsById(id)) {
            throw new ResourceNotFoundException("Purchase not found: " + id);
        }
        purchasedCourseRepository.deleteById(id);
    }


    private PurchasedCourseDTO toDTO(PurchasedCourse pc) {
        if (pc == null) return null;

        PurchasedCourseDTO d = new PurchasedCourseDTO();
        d.setPurchaseId(pc.getPurchaseId());
        d.setStudentId(pc.getStudent().getStudentId());
        d.setCourseId(pc.getCourse().getCourseId());
        d.setPurchaseDate(pc.getPurchaseDate());
        d.setAmountPaid(pc.getAmountPaid());
        d.setStatus(pc.getStatus());
        return d;
    }

    private PurchasedCourse toEntity(PurchasedCourseDTO d, Student s, Course c) {
        if (d == null) return null;

        PurchasedCourse pc = new PurchasedCourse();
        pc.setPurchaseId(d.getPurchaseId());
        pc.setStudent(s);
        pc.setCourse(c);
        pc.setPurchaseDate(d.getPurchaseDate());
        pc.setAmountPaid(d.getAmountPaid());
        pc.setStatus(d.getStatus());
        return pc;
    }

    private void updateEntity(PurchasedCourse pc, PurchasedCourseDTO d, Student s, Course c) {
        pc.setStudent(s);
        pc.setCourse(c);
        pc.setPurchaseDate(d.getPurchaseDate());
        pc.setAmountPaid(d.getAmountPaid());
        pc.setStatus(d.getStatus());
    }
}
