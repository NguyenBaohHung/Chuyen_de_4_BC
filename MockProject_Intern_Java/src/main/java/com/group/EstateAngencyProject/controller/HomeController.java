package com.group.EstateAngencyProject.controller;

import com.group.EstateAngencyProject.constant.PageConstant;
import com.group.EstateAngencyProject.dto.request.HomeBuildingSearchRequest;
import com.group.EstateAngencyProject.dto.response.HomeBuildingSearchResponse;
import com.group.EstateAngencyProject.message.GlobalMessage;
import com.group.EstateAngencyProject.response.APIResponse;
import com.group.EstateAngencyProject.service.IBuildingService;
import com.group.EstateAngencyProject.service.ICategoryService;
import com.group.EstateAngencyProject.service.INewsService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.group.EstateAngencyProject.response.APIResponse.responseBuilder;

@RestController
@RequestMapping("/home")
@AllArgsConstructor
public class HomeController {
    private final IBuildingService buildingService;
    private final ICategoryService categoryService;
    private final INewsService newsService;
//    @PostMapping
//    public List<HomeBuildingSearchResponse> searchResponseList(@RequestBody HomeBuildingSearchRequest homeBuildingSearchRequest){
//        return  buildingService.searchBuilding(homeBuildingSearchRequest);
//    }

    @PostMapping("/search")
    public ResponseEntity<Object> searchResponsePage(
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_NUMBER) int page,
            @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_SIZE) int pageSize,
            @RequestParam(defaultValue = PageConstant.DEFAULT_SORT_BY) String sortBy,
            @RequestBody HomeBuildingSearchRequest request) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, buildingService.searchBuilding(request, pageable));
    }

    @GetMapping("/building-list")
    public ResponseEntity<Object> gettAllBuilding(@RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_SIZE) int pageSize,
                                                  @RequestParam(defaultValue = PageConstant.DEFAULT_SORT_BY) String sortBy) {
        Pageable pageable = PageRequest.of(page, pageSize, Sort.by(sortBy));
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, buildingService.getAllBuilding(pageable));
    }

    @GetMapping("/search/news")

    public ResponseEntity<Object> getSearchNews(@RequestParam("keyword") String key) {
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getSearchNews(key));
    }

    @GetMapping("/cate")
    public ResponseEntity<Object> getAllCategory() {
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, categoryService.getAllCategory());
    }

    //    @GetMapping("/news")
//    public ResponseEntity<Object> getNews(@RequestParam(defaultValue = "0") int page,
//                                          @RequestParam(defaultValue = "5") int size) {
//        Pageable pageable = PageRequest.of(page, size);
////        List<NewsDTO> newsList = newsService.getAllNews(pageable);
////        return new ResponseEntity<>(newsList, HttpStatus.OK);
//        return responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getAllNews(pageable));
//    }
    @GetMapping("/news")
    public ResponseEntity<Object> getSortedNews(@RequestParam(defaultValue = "0") int page,
                                                @RequestParam(defaultValue = "5") int pageSize,
                                                @RequestParam(defaultValue = "createdDate") String sortBy,
                                                @RequestParam(defaultValue = "desc") String sortDir) {
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getSortedNews(page,pageSize,sortBy,sortDir));
    }


    @GetMapping("/news/{id}")
    public ResponseEntity<Object> getNewsById(@PathVariable Integer id){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,newsService.getNewsById(id));
    }

    @GetMapping("/building/{id}")
    public ResponseEntity<Object> getDetailsBuilding(@PathVariable Integer id){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,buildingService.getDetailBuilding(id));
    }
}
