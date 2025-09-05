package com.courseacademy.courseacademyservice.repository;
import com.courseacademy.courseacademyservice.entity.Author;
import org.springframework.data.jpa.repository.JpaRepository;
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
