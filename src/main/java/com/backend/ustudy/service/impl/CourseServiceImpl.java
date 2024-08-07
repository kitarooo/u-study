package com.backend.ustudy.service.impl;

import com.backend.ustudy.dto.request.CourseRequest;
import com.backend.ustudy.entity.Course;
import com.backend.ustudy.exception.NotFoundException;
import com.backend.ustudy.repository.CourseRepository;
import com.backend.ustudy.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ImageUploadServiceImpl imageUploadService;

    @Override
    public String createCourse(CourseRequest request) {
        Course course = new Course();
        course.setName(request.getName());
        course.setDescription(request.getDescription());
        courseRepository.save(course);

        return "Курс успешно создан!";
    }

    @Override
    public String deleteCourse(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс не найден!"));
        courseRepository.delete(course);
        return "Курс успешно удален!";
    }

    @Override
    public String uploadImae(MultipartFile file, Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс не найден!"));
        course.setImageUrl(imageUploadService.saveImage(file));
        courseRepository.save(course);
        return "Изображение успешно добавлено!";
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс не найден!"));
    }

}
