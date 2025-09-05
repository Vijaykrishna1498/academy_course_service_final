package com.courseacademy.courseacademyservice.repository;
import com.courseacademy.courseacademyservice.entity.PurchasedCourse;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PurchasedCourseRepository extends JpaRepository<PurchasedCourse, Long> {

}
