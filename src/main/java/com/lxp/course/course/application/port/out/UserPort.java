package com.lxp.course.course.application.port.out;

import com.lxp.course.course.application.port.out.dto.UserDetailDto;

public interface UserPort {
    UserDetailDto getUserDetail(Long instructorId);
}
