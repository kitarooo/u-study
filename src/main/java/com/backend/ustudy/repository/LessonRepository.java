package com.backend.ustudy.repository;

import com.backend.ustudy.entity.Lesson;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    @Query(value = "SELECT * FROM lessons l WHERE l.course_id= :courseId AND l.id= :id", nativeQuery = true)
    Optional<Lesson> findByIdAndCourseId(@Param("courseId") Long courseId, @Param("id") Long id);
    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = "DELETE FROM lessons l WHERE l.course_id= :courseId", nativeQuery = true)
    void delete(@Param("courseId") Long courseId);
}
