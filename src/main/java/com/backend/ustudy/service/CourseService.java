package com.backend.ustudy.service;


import com.backend.ustudy.dto.request.CourseRequest;
import com.backend.ustudy.entity.Course;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CourseService {

    String createCourse(CourseRequest course);
    String deleteCourse(Long id);
    String uploadImae(MultipartFile file, Long id);
    List<Course> getAllCourses();
    Course getCourseById(Long id);

}
