package com.backend.ustudy.service.impl;

import com.backend.ustudy.dto.request.LessonRequest;
import com.backend.ustudy.dto.response.LessonResponse;
import com.backend.ustudy.entity.Course;
import com.backend.ustudy.entity.Lesson;
import com.backend.ustudy.exception.NotFoundException;
import com.backend.ustudy.repository.CourseRepository;
import com.backend.ustudy.repository.LessonRepository;
import com.backend.ustudy.service.LessonService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ImageUploadServiceImpl imageUploadService;

    @Override
    public String createLesson(LessonRequest request) {
        Course course = courseRepository.findById(request.getCourse().getId())
                .orElseThrow(() -> new NotFoundException("Курс не найден!"));

        Lesson lesson = Lesson.builder()
                .course(course)
                .name(request.getName())
                .build();
        lessonRepository.save(lesson);

        return "Урок успешно добавлен!";
    }

    @Override
    public String deleteLesson(Long courseId, Long id) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new NotFoundException("Курс не найден!"));
        if (course.getId().equals(courseId)) {
            lessonRepository.deleteById(id);
        }
        return "Урок успешно удален!";
    }

    @Override
    public List<LessonResponse> getAllLessonsByCourseId(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Курс не найден!"));
        return lessonRepository.findAll().stream()
                .filter(lesson -> lesson.getCourse().equals(course))
                .map(this::toResponse)
                .toList();
    }

    @Override
    public LessonResponse getLessonById(Long courseId, Long id) {
        return lessonRepository.findByIdAndCourseId(courseId, id)
                .map(this::toResponse)
                .orElseThrow(() -> new NotFoundException("Урок не найден!"));
    }

    @Override
    public String uploadImageAndVideo(MultipartFile multipartFile1 ,MultipartFile multipartFile2, Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Урок не найден!"));
        lesson.setImageUrl(imageUploadService.saveImage(multipartFile1));
        lesson.setVideoUrl(imageUploadService.saveMedia(multipartFile2));
        lessonRepository.save(lesson);

        return "Видео и Изображение успешно добавлены!";
    }

    public LessonResponse toResponse(Lesson lesson) {
        return LessonResponse.builder()
                .id(lesson.getId())
                .imageUrl(lesson.getImageUrl())
                .name(lesson.getName())
                .videoUrl(lesson.getVideoUrl())
                .build();
    }
}
