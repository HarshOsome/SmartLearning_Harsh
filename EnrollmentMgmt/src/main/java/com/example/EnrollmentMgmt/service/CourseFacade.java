
package com.example.EnrollmentMgmt.service;

import org.springframework.stereotype.Service;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;

import com.example.EnrollmentMgmt.client.CourseClient;
import com.example.EnrollmentMgmt.client.CourseDto;
import com.example.EnrollmentMgmt.resilience.Fallbacks;

@Service
@RequiredArgsConstructor
public class CourseFacade {

    private final CourseClient courseClient;

    // CB wraps the Feign call; fallback returns degraded CourseDto
    @CircuitBreaker(name = "CourseService", fallbackMethod = "getCourseFallback")
    public CourseDto getCourseResilient(Long courseId) {
        return courseClient.getCourse(courseId);
    }

    // Signature MUST match args + Throwable, and return CourseDto
    protected CourseDto getCourseFallback(Long courseId, Throwable ex) {
        return Fallbacks.degradeCourse(courseId, ex); // status=UNKNOWN, hours=0
    }
}
