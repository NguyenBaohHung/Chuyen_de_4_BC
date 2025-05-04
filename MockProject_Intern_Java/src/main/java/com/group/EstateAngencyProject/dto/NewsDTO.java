package com.group.EstateAngencyProject.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewsDTO extends AbstractDTO{
    private Integer newsId;
    private String title;
    private String content;
    private String image;
}
