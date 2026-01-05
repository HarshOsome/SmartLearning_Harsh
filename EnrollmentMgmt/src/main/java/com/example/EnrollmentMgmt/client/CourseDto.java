
package com.example.EnrollmentMgmt.client;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class CourseDto {
    private Long id;
    private String title;
    private String description;
    private String status;         
    private int estimatedHours; 
    private Long instructorUserId;
    private LocalDateTime createdAt;
    private LocalDateTime updateAt;
}
