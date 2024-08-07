package com.backend.ustudy.controller;

import com.backend.ustudy.dto.request.CourseRequest;
import com.backend.ustudy.entity.Course;
import com.backend.ustudy.entity.User;
import com.backend.ustudy.exception.handler.ExceptionResponse;
import com.backend.ustudy.service.impl.CourseServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseServiceImpl courseService;

    @PostMapping("/create")
    public String createCourse(@RequestBody CourseRequest request) {
        return courseService.createCourse(request);
    }

    @DeleteMapping("/delete/{id}")
    @Operation(summary = "Удаление курса", description = "Ендпоинт для удаления курса",
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
    public String deleteCourse(@PathVariable Long id) {
        return courseService.deleteCourse(id);
    }

    @GetMapping("/all")
    public List<Course> getAllCourses() {
        return courseService.getAllCourses();
    }

    @GetMapping("{id}")
    @Operation(summary = "Получение курса по id", description = "Ендпоинт для получения курса",
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
    public Course getCourseById(@PathVariable Long id) {
        return courseService.getCourseById(id);
    }

    @PatchMapping("/updateImage/{id}")
    @Operation(summary = "Добавить изображения для курса", description = "Ендпоинт для добавления изображения",
            responses = {
                    @ApiResponse(
                            content = @Content(mediaType = "string"),
                            responseCode = "200", description = "Good"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "File's empty!"),
                    @ApiResponse(
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ExceptionResponse.class)),
                            responseCode = "404", description = "Course not found!"
                    )

            })
    public String updateProfilePhoto(@RequestParam MultipartFile multipartFile, @PathVariable Long id) throws IOException {
        return courseService.uploadImae(multipartFile, id);
    }
}
