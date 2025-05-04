package com.group.EstateAngencyProject.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class HomeBuildingSearchResponse {
    private Integer buildingId;
    private Integer bedRoom;
    private Integer bathRoom;
    private String address;
    private String buildingName;
    private Float area;
    private BigDecimal price;
    private String priceDescription;
    private String juridical;
    private String image;
    private String map;
    private String direction;
    private Boolean isRent;
    private Boolean isSell;
    private List<Integer> categoryId;
    private String mangerName;
}
