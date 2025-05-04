package com.group.EstateAngencyProject.dto.paging;

import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.List;

@Data
public class APIPageableDTO implements Serializable {
    private int pageNumber;
    private Object content;
    private int pageSize;
//    private int offset;
//    private int numberOfElement;
    private long totalElements;
    private int totalPages;
//    private boolean sorted;
//    private boolean first;
//    private boolean last;
//    private boolean empty;

//    public <T> APIPageableDTO(Page<T> page){
//        Pageable pageable = page.getPageable();
//        setPageNumber(page.getNumber());
//        setPageSize(page.getSize());
//        setContent(page.getContent());
//        setNumberOfElement(page.getNumberOfElements());
//        setTotalElements(page.getTotalElements());
//        setTotalPages(page.getTotalPages());
//        setFirst(page.isFirst());
//        setLast(page.isLast());
//        setEmpty(page.isEmpty());
//        setSorted(pageable.getSort().isSorted());
//    }

    public <T>  APIPageableDTO responsePageBuilder(Page<T> page,Object contents){
        APIPageableDTO apiPageableDTO = new APIPageableDTO();
        setPageNumber(page.getNumber());
        setPageSize(page.getSize());
        setContent(contents);
        setTotalElements(page.getTotalElements());
        setTotalPages(page.getTotalPages());
        return apiPageableDTO;
    }
}
