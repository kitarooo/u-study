package com.backend.ustudy.service;

import com.backend.ustudy.dto.request.LessonRequest;
import com.backend.ustudy.dto.response.LessonResponse;
import com.backend.ustudy.entity.Lesson;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface LessonService {

    String createLesson(LessonRequest request);
    String deleteLesson(Long courseId, Long id);
    List<LessonResponse> getAllLessonsByCourseId(Long id);
    LessonResponse getLessonById(Long courseId, Long id);
    String uploadImageAndVideo(MultipartFile multipartFile1, MultipartFile multipartFile2, Long id);
}
