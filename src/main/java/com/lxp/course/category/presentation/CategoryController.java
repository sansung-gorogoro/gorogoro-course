package com.lxp.course.category.presentation;

import com.lxp.course.category.application.port.in.GetCategoriesUseCase;
import com.lxp.course.category.presentation.request.CreateCategoryRequest;
import com.lxp.course.category.presentation.response.CategoriesResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/categories")
public class CategoryController {
    private final GetCategoriesUseCase getCategoriesUseCase;

    @PostMapping
    public ResponseEntity<Void> createCategory(@Valid @RequestBody CreateCategoryRequest request) {


        return ResponseEntity.ok().build();
    }

    @GetMapping
    public CategoriesResponse getCategories() {
        return CategoriesResponse.of(getCategoriesUseCase.getCategoriesExecute());
    }
}
