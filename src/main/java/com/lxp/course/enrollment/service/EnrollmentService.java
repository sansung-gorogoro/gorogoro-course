package com.lxp.course.enrollment.service;

import com.lxp.course.course.application.port.in.GetCourseUseCase;
import com.lxp.course.course.application.port.in.dto.CourseDetailDto;
import com.lxp.course.enrollment.domain.Enrollment;
import com.lxp.course.enrollment.repository.EnrollmentRepository;
import com.lxp.course.enrollment.service.dto.CreateEnrollmentDto;
import com.lxp.course.enrollment.service.dto.EnrolledCourseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final GetCourseUseCase getCourseUseCase;

    public void createEnrollment(CreateEnrollmentDto dto) {
        Enrollment enrollment = Enrollment.create(dto.userId(), dto.courseId());

        enrollmentRepository.save(enrollment);
    }

    public List<EnrolledCourseDto> getEnrolledCourses(Long userId) {
        List<Long> courseIds = enrollmentRepository.findAllByUserId(userId)
            .stream().map(Enrollment::getCourseId).toList();

        List<CourseDetailDto> dtos = getCourseUseCase.getCourseDetails(courseIds);

        return dtos.stream().map(dto ->
            new EnrolledCourseDto(
                dto.courseId(),
                dto.title(),
                dto.instructorName(),
                dto.coverImageUrl()
            )
        ).toList();
    }

    public Boolean isEnrolled(Long userId, Long courseId) {
        return enrollmentRepository.existsByUserIdAndCourseId(userId, courseId);
    }
}
