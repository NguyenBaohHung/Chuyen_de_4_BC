package com.group.EstateAngencyProject.controller;

import com.group.EstateAngencyProject.dto.CategoryDTO;
import com.group.EstateAngencyProject.message.GlobalMessage;

import com.group.EstateAngencyProject.dto.NewsDTO;
import com.group.EstateAngencyProject.response.APIResponse;

import com.group.EstateAngencyProject.service.impl.NewsService;
import org.springdoc.core.RequestBodyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static com.group.EstateAngencyProject.response.APIResponse.responseBuilder;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsServide) {
        this.newsService = newsServide;
    }

    @PostMapping
    public ResponseEntity<Object> createOrUpdateNews(@RequestBody NewsDTO newsDTO) {
        return responseBuilder(HttpStatus.CREATED, GlobalMessage.SUCCESS, newsService.createOrUpdateNews(newsDTO));
    }

//    @DeleteMapping
//    public ResponseEntity<Object> deleteNews(@RequestBody NewsDTO newsDTO) {
//        return responseBuilder(HttpStatus.CREATED, GlobalMessage.SUCCESS, newsService.deleteNews(newsDTO));
//    }

    @GetMapping
    public ResponseEntity<Object> getNews(@RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size);
//        List<NewsDTO> newsList = newsService.getAllNews(pageable);
//        return new ResponseEntity<>(newsList, HttpStatus.OK);
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getAllNews(pageable));
    }

//    @GetMapping
//    public ResponseEntity<Object> getSortedNews(@RequestParam(defaultValue = "0") int page,
//                                                @RequestParam(defaultValue = "5") int size,
//                                                @RequestParam(defaultValue = "createdDate") String sortBy,
//                                                @RequestParam(defaultValue = "desc") String sortDir) {
//        return responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getSortedNews(page, size, sortBy, sortDir));
//    }

    @GetMapping("/search")

    public ResponseEntity<Object> getSearchNews(@RequestParam("keyword") String key){
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getSearchNews(key));
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getPageByUserEntity(@RequestParam(defaultValue = "0") int page,
                                                      @RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page, size);
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getPageByUserEntity(pageable));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteNews(@PathVariable Integer id) {
//        List<NewsDTO> results = new ArrayList<>();
//        for(Integer id : ids){
//            NewsDTO newsDTO = newsService.getNewsById(id);
//        }
//        if (ids.size() > 0){
//            newsService.deleteNews(ids);
//        }
//        if(ids.isEmpty()){
//            return ResponseEntity.badRequest().body("No IDs provided");
//        }
        NewsDTO newsDTO = newsService.getNewsById(id);
        newsService.deleteNews(id);
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getNewsById(@PathVariable Integer id) {
        return responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS, newsService.getNewsById(id));
    }
}