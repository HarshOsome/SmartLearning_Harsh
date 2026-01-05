package com.example.EnrollmentMgmt.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.EnrollmentMgmt.client.CourseClient;
import com.example.EnrollmentMgmt.client.CourseDto;
import com.example.EnrollmentMgmt.client.UserClient;
import com.example.EnrollmentMgmt.client.UserDto;
import com.example.EnrollmentMgmt.entity.Enrollment;
import com.example.EnrollmentMgmt.exception.UpstreamUnavailableException;
import com.example.EnrollmentMgmt.repository.EnrollmentRepository;

//import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.springframework.transaction.annotation.Transactional;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
	private final EnrollmentRepository repo;
	private final UserClient userClient;
	private final CourseClient courseClient;
	private final CourseFacade courseFacade;

	@Transactional
	@CircuitBreaker(name = "CourseService", fallbackMethod = "enrollFallback")
	public Enrollment enroll(Long userId, Long courseId) {

		UserDto user = userClient.getUser(userId);
		CourseDto course = courseFacade.getCourseResilient(courseId);

		if (!"STUDENT".equalsIgnoreCase(user.getRole())) {
			throw new IllegalStateException("Only Student role can enroll");
		}
		if (!"PUBLISHED".equalsIgnoreCase(course.getStatus())) {
			throw new IllegalStateException("Course must be PUBLISHED to enroll(status = " + course.getStatus() + ")");
		}

		repo.findByUserIdAndCourseId(userId, courseId).ifPresent(e -> {
			throw new IllegalStateException("Already enrolled");
		});
		Enrollment e = new Enrollment();
		e.setUserId(userId);
		e.setCourseId(courseId);
		e.setStatus(Enrollment.Status.ACTIVE);
		e.setEnrolledAt(LocalDateTime.now());
		return repo.save(e);

	}

	@Transactional
	public Enrollment complete(Long userId, Long courseId) {
		Enrollment e = repo.findByUserIdAndCourseId(userId, courseId)
				.orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
		e.setStatus(Enrollment.Status.COMPLETED);
		e.setCompletedAt(LocalDateTime.now());
		return repo.save(e);

	}
	// FALLBACK METHOD
	protected Enrollment enrollFallback(Long userId, Long courseId, Throwable ex) {
		throw new UpstreamUnavailableException("Course service unavailable", ex);
	}

	@Transactional
	public void cancel(Long userId, Long courseId) {
		Enrollment e = repo.findByUserIdAndCourseId(userId, courseId)
				.orElseThrow(() -> new IllegalArgumentException("Enrollment not found"));
		e.setStatus(Enrollment.Status.CANCELLED);
		repo.save(e);
	}

	public List<Enrollment> getByUser(Long userId) {
		// TODO Auto-generated method stub
		return repo.findByUserId(userId);
	}

	public List<Enrollment> getByCourse(Long courseId) {
		// TODO Auto-generated method stub
		return repo.findByCourseId(courseId);
	}
	

}
