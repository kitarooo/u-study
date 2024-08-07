package com.backend.ustudy.dto.request;


import com.backend.ustudy.entity.Course;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LessonRequest {
    String name;
    Course course;
}
