package com.example.EnrollmentMgmt.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "course-service", url = "http://localhost:4446", path = "/api/courses")
public interface CourseClient {

	@GetMapping("/{id}")
	CourseDto getCourse(@PathVariable("id") Long id);

	@GetMapping("/{courseId}/modules/{moduleId}")
	ModuleDto getModule(@PathVariable("courseId") Long courseId, @PathVariable("moduleId") Long moduleId);

}
