package com.lxp.course.course.infra;

import com.lxp.course.course.application.port.out.UserPort;
import com.lxp.course.course.application.port.out.dto.UserDetailDto;
import com.lxp.course.course.infra.feign.UserFeign;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPortAdaptor implements UserPort {
    private final UserFeign userFeign;

    @Override
    public UserDetailDto getUserDetail(Long instructorId) {
        return userFeign.findCourseDetailsByIds(instructorId);
    }
}
