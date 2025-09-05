package com.courseacademy.courseacademyservice.repository;
import com.courseacademy.courseacademyservice.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
public interface CourseRepository extends JpaRepository<Course, Long> {

}
