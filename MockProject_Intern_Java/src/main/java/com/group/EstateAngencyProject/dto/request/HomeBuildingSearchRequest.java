package com.group.EstateAngencyProject.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HomeBuildingSearchRequest {
    private Integer buildingId;
    private String buildingName;
    private String street;
    private String ward;
    private String district;
    private Float areaFrom;
    private Float areaTo;

    private BigDecimal priceFrom;
    private BigDecimal priceTo;

    private String direction;
    private Boolean isRent;
    private Boolean isSell;
    private Integer bedRoom;
    private List<Integer> categoryId;
//    private Integer userId;
}
