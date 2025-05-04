package com.group.EstateAngencyProject.service;

import com.group.EstateAngencyProject.dto.NewsDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface INewsService {
    NewsDTO createOrUpdateNews(NewsDTO newsDTO);
    List<NewsDTO>  getAllNews(Pageable pageable);

    APIPageableDTO getSortedNews(int page, int size, String sortBy, String sortDir);

    List<NewsDTO> getSearchNews(String key);

    List<NewsDTO> getPageByUserEntity(Pageable pageable);

    void deleteNews(Integer id);

    NewsDTO getNewsById(Integer newsId);

}

