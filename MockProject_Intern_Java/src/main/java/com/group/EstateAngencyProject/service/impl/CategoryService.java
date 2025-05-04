package com.group.EstateAngencyProject.service.impl;

import com.group.EstateAngencyProject.converter.CategoryConverter;
import com.group.EstateAngencyProject.dto.CategoryDTO;
import com.group.EstateAngencyProject.dto.NewsDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.entity.CategoryEntity;
import com.group.EstateAngencyProject.repository.CategoryRepository;
import com.group.EstateAngencyProject.service.ICategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService implements ICategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryConverter categoryConverter;

    @Override
    public List<CategoryDTO> getAllCategory() {
        List<CategoryEntity> categoryList = categoryRepository.findAll();
        return categoryList.stream().map(categoryConverter::toCategoryDTO).collect(Collectors.toList());
    }

    @Override
    public List<CategoryDTO> getAllPageCategory(Pageable pageable) {
        Page<CategoryEntity> categoryEntityPage = categoryRepository.findAll(pageable);
        List<CategoryDTO> contents = categoryEntityPage.stream().map(categoryConverter::toCategoryDTO).collect(Collectors.toList());
        APIPageableDTO apiPageableDTO = new APIPageableDTO();
        apiPageableDTO.responsePageBuilder(categoryEntityPage,contents);
        return contents;
    }

    @Override
    public CategoryDTO getCategoryById(Integer categoryId) {
        CategoryEntity categoryEntity = categoryRepository.findById(categoryId).get();
        return categoryConverter.toCategoryDTO(categoryEntity);
    }

    @Override
    public CategoryDTO createOrUpdateCategory(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = categoryConverter.toCategoryEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
        return categoryConverter.toCategoryDTO(categoryEntity);
    }

    @Transactional
    @Override
    public void deleteCategory(Integer id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).get();
        categoryRepository.delete(categoryEntity);
    }

}
