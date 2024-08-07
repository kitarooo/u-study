package com.backend.ustudy.controller;

import com.backend.ustudy.dto.request.LessonRequest;
import com.backend.ustudy.dto.response.LessonResponse;
import com.backend.ustudy.exception.handler.ExceptionResponse;
import com.backend.ustudy.service.impl.LessonServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/lessons")
@RequiredArgsConstructor
public class LessonController {

    private final LessonServiceImpl lessonService;

    @PostMapping("/create")
    @Operation(summary = "Создание урока", description = "Ендпоинт для создания урока",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "Course not found"
                    )
            })
    public String createLesson(@RequestBody LessonRequest lesson) {
        return lessonService.createLesson(lesson);
    }

    @DeleteMapping("/{courseId}/{id}")
    @Operation(summary = "Удаление урока", description = "Ендпоинт для удаления урока",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "Course not found"
                    )
            })
    public String deleteLesson(@PathVariable Long courseId, @PathVariable Long id) {
        return lessonService.deleteLesson(courseId, id);
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение по course_id", description = "Ендпоинт для получения урока",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "Course not found"
                    )
            })
    public List<LessonResponse> getAll(@PathVariable Long id) {
        return lessonService.getAllLessonsByCourseId(id);
    }

    @GetMapping("/{courseId}/{id}")
    @Operation(summary = "Получение по id", description = "Ендпоинт для получения урока по id",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "Lesson not found"
                    )
            })
    public LessonResponse getById(@PathVariable Long courseId, @PathVariable Long id) {
        return lessonService.getLessonById(courseId, id);
    }

    @PatchMapping("/upload/{id}")
    @Operation(summary = "Добавление видео и изображения для урока",
            description = "Ендпоинт для добавления видео и изображения для урока по id",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "Lesson not found"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "File's empty!")
            })
    public String updateUrls(@RequestParam MultipartFile multipartFile1,
                             @RequestParam MultipartFile multipartFile2,
                             @PathVariable Long id) {
        return lessonService.uploadImageAndVideo(multipartFile1, multipartFile2, id);
    }
}
