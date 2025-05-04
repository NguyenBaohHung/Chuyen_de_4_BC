package com.group.EstateAngencyProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO extends AbstractDTO{
    private Integer categoryId;
    private String categoryCode;
    private String categoryDes;
}
