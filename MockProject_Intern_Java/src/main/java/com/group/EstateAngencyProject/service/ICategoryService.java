package com.group.EstateAngencyProject.service;

import com.group.EstateAngencyProject.dto.CategoryDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getAllCategory();

    List<CategoryDTO> getAllPageCategory(Pageable pageable);

    CategoryDTO getCategoryById(Integer categoryId);

    CategoryDTO createOrUpdateCategory(CategoryDTO categoryDTO);


    void deleteCategory(Integer id);
}
