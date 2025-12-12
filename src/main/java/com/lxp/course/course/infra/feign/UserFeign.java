package com.lxp.course.course.infra.feign;

import com.lxp.course.course.application.port.out.dto.UserDetailDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
    name = "user-client",
    url = "${feign.client.config.user-service.url}"
)
public interface UserFeign {
    @GetMapping
    UserDetailDto findCourseDetailsByIds(@RequestParam Long userId);
}
