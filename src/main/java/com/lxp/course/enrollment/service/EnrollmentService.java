package com.lxp.course.enrollment.service;

import com.lxp.course.enrollment.domain.Enrollment;
import com.lxp.course.enrollment.repository.EnrollmentRepository;
import com.lxp.course.enrollment.service.dto.CreateEnrollmentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public void createEnrollment(CreateEnrollmentDto dto) {
        Enrollment enrollment = Enrollment.create(dto.userId(), dto.courseId());

        enrollmentRepository.save(enrollment);
    }
}
