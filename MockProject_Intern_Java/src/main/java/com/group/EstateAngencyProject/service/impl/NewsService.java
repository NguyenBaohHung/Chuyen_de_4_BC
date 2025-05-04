package com.group.EstateAngencyProject.service.impl;

import com.group.EstateAngencyProject.converter.NewsConverter;
import com.group.EstateAngencyProject.dto.NewsDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.entity.NewsEntity;
import com.group.EstateAngencyProject.entity.UserEntity;
import com.group.EstateAngencyProject.repository.NewsRepository;
import com.group.EstateAngencyProject.repository.UserRepository;
import com.group.EstateAngencyProject.service.INewsService;
import com.group.EstateAngencyProject.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewsService implements INewsService {
    private NewsConverter newsConverter;
    private NewsRepository newsRepository;
    private UserRepository userRepository;


    @Autowired
    public NewsService(NewsConverter newsConverter, NewsRepository newsRepository,UserRepository userRepository) {
        this.newsConverter = newsConverter;
        this.newsRepository = newsRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NewsDTO createOrUpdateNews(NewsDTO newsDTO) {
        NewsEntity newsEntity = newsConverter.toNewsEntity(newsDTO);
        String userName = SecurityUtils.getPrincipal();
        UserEntity userEntity = userRepository.findByUserNameAndIsActive(userName,true).get();
        newsEntity.setUserEntity(userEntity);
        NewsEntity storedNewsEntity = newsRepository.save(newsEntity);
        return newsConverter.toNewsDTO(storedNewsEntity);
    }

//    @Override
//    public String deleteNews(NewsDTO newsDTO) {
//        newsRepository.deleteById(newsDTO.getNewsId());
//        return "Successfully deleted";
//    }

    @Override
    public List<NewsDTO> getAllNews(Pageable pageable) {
        Page<NewsEntity> newsEntities = newsRepository.findAll(pageable);
        List<NewsDTO> contents = newsEntities.stream().map(newsConverter::toNewsDTO).collect(Collectors.toList());
//        APIPageableDTO apiPageableDTO = new APIPageableDTO();
//        apiPageableDTO.responsePageBuilder(newsEntities,contents);
        return contents;
    }

    @Override
    public APIPageableDTO getSortedNews(int page, int size, String sortBy, String SortDir) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<NewsEntity> newsEntities = newsRepository.findAll(pageable);
        List<NewsDTO> contents =  newsEntities.map(newsConverter::toNewsDTO).stream().collect(Collectors.toList());
        APIPageableDTO apiPageableDTO = new APIPageableDTO();
        apiPageableDTO.responsePageBuilder(newsEntities,contents);
        return apiPageableDTO;
    }

    @Override
    public List<NewsDTO> getSearchNews(String key) {
        List<NewsEntity> searchList = newsRepository.findByTitleContainingIgnoreCase(key);
        return searchList.stream().map(newsConverter::toNewsDTO).collect(Collectors.toList());
    }

    @Override
    public List<NewsDTO> getPageByUserEntity(Pageable pageable) {
        String userName = SecurityUtils.getPrincipal();
        UserEntity userEntity = userRepository.findByUserNameAndIsActive(userName,true).get();
        Page<NewsEntity> newsEntityPage = newsRepository.findByUserEntity(userEntity,pageable);
        List<NewsDTO> contents =  newsEntityPage.stream().map(newsConverter::toNewsDTO).collect(Collectors.toList());
        APIPageableDTO apiPageableDTO = new APIPageableDTO();
        apiPageableDTO.responsePageBuilder(newsEntityPage,contents);
        return contents;
    }

    @Override
    public void deleteNews(Integer id) {
//        newsRepository.deleteByNewsIdIn(ids);
        newsRepository.deleteById(id);
    }

    @Override
    public NewsDTO getNewsById(Integer newsId) {
        NewsEntity newsEntity = newsRepository.findById(newsId).get();
        return newsConverter.toNewsDTO(newsEntity);
    }


}