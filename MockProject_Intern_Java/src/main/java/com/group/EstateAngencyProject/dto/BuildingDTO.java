package com.group.EstateAngencyProject.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class BuildingDTO extends AbstractDTO{
    private Integer buildingId;
    private String district;
    private String ward;
    private String street;
    private String image;
    private String map;
    private BigDecimal price;
    private String buildingName;
    private Integer bedRoom;
    private String direction;
    private String priceDescription;
    private Integer bathRoom;
    private String juridical;
    private Float area;
    private Boolean isRent;
    private Boolean isSell;
    private Boolean status;
    private List<Integer> categoryId;
//    private Integer
}
