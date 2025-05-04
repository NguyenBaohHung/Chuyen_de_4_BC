package com.group.EstateAngencyProject.controller;

import com.group.EstateAngencyProject.constant.PageConstant;
import com.group.EstateAngencyProject.dto.CategoryDTO;
import com.group.EstateAngencyProject.dto.TransactionDTO;
import com.group.EstateAngencyProject.message.GlobalMessage;
import com.group.EstateAngencyProject.response.APIResponse;
import com.group.EstateAngencyProject.service.impl.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/list")
    public ResponseEntity<Object> getAllPageCategory(@RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_NUMBER) int page,
                                                     @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_SIZE) int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS,categoryService.getAllPageCategory(pageable));
    }

    @GetMapping
    public ResponseEntity<Object> getAllCategory(){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,categoryService.getAllCategory());
    }

    @PostMapping
    public ResponseEntity<Object> createOrUpdateCategory(@RequestBody CategoryDTO categoryDTO){
        return APIResponse.responseBuilder(HttpStatus.CREATED,GlobalMessage.SUCCESS,categoryService.createOrUpdateCategory(categoryDTO));
    }


    @GetMapping("/{id}")
    public ResponseEntity<Object> getCategoryById(@PathVariable Integer id){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,categoryService.getCategoryById(id));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCategory(@PathVariable Integer id){
//        List<CategoryDTO> results = new ArrayList<>();
//        for(Integer item : ids){
//            CategoryDTO categoryDTO = categoryService.getCategoryById(item);
//            results.add(categoryDTO);
//        }
//        if(ids.size()>0){
//            categoryService.deleteCategory(ids);
//        }
//        if (ids.isEmpty()) {
//            return ResponseEntity.badRequest().body("No IDs provided");
//        }
        CategoryDTO categoryDTO = categoryService.getCategoryById(id);
        categoryService.deleteCategory(id);
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS,categoryDTO);
    }
}
