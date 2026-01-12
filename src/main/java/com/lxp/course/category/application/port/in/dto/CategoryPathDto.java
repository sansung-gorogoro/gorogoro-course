package com.lxp.course.category.application.port.in.dto;

public record CategoryPathDto(
    Long childId,
    String childName,
    Long parentId,
    String parentName
) {
}
