
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
    private String status;          // or use an enum if you prefer
    private int estimatedHours; // match server type
    private Long instructorUserId;

    // Use the same names the server sends; no defaults here
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updateAt; // keep as-is if server sends "updateAt"
    // If you rename server field to "updatedAt", rename this to "updatedAt" too.
}
