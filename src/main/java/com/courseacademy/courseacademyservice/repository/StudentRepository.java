package com.courseacademy.courseacademyservice.repository;
import com.courseacademy.courseacademyservice.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
public interface StudentRepository extends JpaRepository<Student, Long> {

}
