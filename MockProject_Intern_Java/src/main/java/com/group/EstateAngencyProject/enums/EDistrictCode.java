package com.group.EstateAngencyProject.enums;

import java.util.Map;
import java.util.TreeMap;

public enum EDistrictCode {
    NGU_HANH_SON("quận Ngũ Hành Sơn"),
    HAI_CHAU("quận Hải Châu"),
    LIEN_CHIEU("quận Liên Chiểu"),
    SON_TRA("quận Sơn Trà"),
    THANH_KHE("quận Thanh Khê"),
    HOA_VANG("huyện Hòa Vang"),
    HOANG_SA("huyện Hoàng Sa");
    private final String districtName;
    EDistrictCode(String districtName){
        this.districtName = districtName;
    }
    public String getDistrictName() {
        return districtName;
    }

    public static Map<String,String> getDistrict(){
        Map<String,String> map = new TreeMap<>();
        for(EDistrictCode item : EDistrictCode.values()){
            map.put(item.toString(),item.getDistrictName());
        }
        return map;
    }
}
