package com.lxp.course.category.presentation;

import com.lxp.course.category.application.port.in.GetCategoriesUseCase;
import com.lxp.course.category.presentation.response.CategoriesResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final GetCategoriesUseCase getCategoriesUseCase;

    @GetMapping
    public CategoriesResponse getCategories() {
        return CategoriesResponse.of(getCategoriesUseCase.getCategoriesExecute());
    }
}
