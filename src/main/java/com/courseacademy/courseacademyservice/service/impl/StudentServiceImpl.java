package com.courseacademy.courseacademyservice.service.impl;

import com.courseacademy.courseacademyservice.dto.StudentDTO;
import com.courseacademy.courseacademyservice.entity.Student;
import com.courseacademy.courseacademyservice.exception.ResourceNotFoundException;
import com.courseacademy.courseacademyservice.repository.StudentRepository;
import com.courseacademy.courseacademyservice.service.StudentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    public StudentServiceImpl(StudentRepository studentRepository) {

        this.studentRepository = studentRepository;
    }


    private StudentDTO toDTO(Student s) {
        if (s == null) return null;
        StudentDTO d = new StudentDTO();
        d.setStudentId(s.getStudentId());
        d.setStudentName(s.getStudentName());
        d.setStudentEmail(s.getStudentEmail());
        d.setStudentPassword(s.getStudentPassword());
        return d;
    }

    private Student toEntity(StudentDTO d) {
        Student s = new Student();
        s.setStudentId(d.getStudentId());
        s.setStudentName(d.getStudentName());
        s.setStudentEmail(d.getStudentEmail());
        s.setStudentPassword(d.getStudentPassword());
        return s;
    }

    private void updateEntity(Student s, StudentDTO d) {
        s.setStudentName(d.getStudentName());
        s.setStudentEmail(d.getStudentEmail());
        s.setStudentPassword(d.getStudentPassword());
    }


    @Override
    public StudentDTO create(StudentDTO dto) {
        Student s = toEntity(dto);
        return toDTO(studentRepository.save(s));
    }

    @Override
    public StudentDTO getById(Long id) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        return toDTO(s);
    }

    @Override
    public List<StudentDTO> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public StudentDTO update(Long id, StudentDTO dto) {
        Student s = studentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found: " + id));
        updateEntity(s, dto);
        return toDTO(s);
    }

    @Override
    public void delete(Long id) {
        if (!studentRepository.existsById(id)) {
            throw new ResourceNotFoundException("Student not found: " + id);
        }
        studentRepository.deleteById(id);
    }
}
