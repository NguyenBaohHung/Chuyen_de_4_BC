package com.group.EstateAngencyProject.repository.custom.impl;

import com.group.EstateAngencyProject.dto.request.HomeBuildingSearchRequest;
import com.group.EstateAngencyProject.entity.BuildingEntity;
import com.group.EstateAngencyProject.repository.custom.BuildingRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class BuildingRepositoryImpl implements BuildingRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    private String multitableJoin(HomeBuildingSearchRequest request){
        String sql="";
        List<Integer> categoryId = request.getCategoryId();
        if(categoryId!=null&&categoryId.size()>0){
            sql += " inner join buildingtype as bt on bt.building_id = b.building_id";
        }else{
            sql+="";
        }
        return sql;
    }

    private String  simpleQuery(HomeBuildingSearchRequest request){
        String sql ="";
        try {
            Field[] fields = HomeBuildingSearchRequest.class.getDeclaredFields();
            for(Field item : fields){
                item.setAccessible(true);
                String fieldName = item.getName();
                if(!fieldName.equals("categoryId")&&!fieldName.startsWith("price")
                &&!fieldName.startsWith("area")&&!fieldName.startsWith("is")){
                    Object value = item.get(request);
                    if(value!=null){
                        if(item.getType().getName().equals("java.lang.String")&&!value.toString().equals("")){
                            sql+=" and b."+fieldName.toLowerCase()+" like '%"+value+"%'";
                        }
                        if(item.getType().getName().equals("java.lang.Integer")){
                            sql+=" and b.bed_room = "+value;
                        }
                    }
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return sql;
    }

    private String specialQuery(HomeBuildingSearchRequest request){
        String sql ="";
        Float areaFrom = request.getAreaFrom();
        Float areaTo = request.getAreaTo();
        BigDecimal priceFrom = request.getPriceFrom();
        BigDecimal priceTo = request.getPriceTo();
        List<Integer> categoryId = request.getCategoryId();
        Boolean isRent = request.getIsRent();
        Boolean isSell = request.getIsSell();
        if(areaFrom!=null){
            sql+=" and b.area >= "+areaFrom;
        }
        if(areaTo!=null){
            sql+=" and b.area <= "+areaTo;
        }
        if(priceFrom != null){
            sql+=" and b.price >= "+priceFrom;
        }
        if(priceTo!=null){
            sql+=" and b.price <= "+priceTo;
        }
        if(isRent!=null){
            sql+=" and b.is_rent = "+isRent;
        }
        if(isSell!=null){
            sql+=" and b.is_sell = "+isSell;
        }
        if(categoryId!=null&&categoryId.size()>0){
            sql+=" and bt.category_id in( ";
            String query = categoryId.stream().map(it->"'"+it.toString()+"'").collect(Collectors.joining(","));
            sql+=query;
            sql+=") ";
        }
        return sql;
    }

    @Override
    public List<BuildingEntity> searchHomeBuilding(HomeBuildingSearchRequest buildingSearchRequest) {
        String sql = " Select b.* from building as b";
        sql+=multitableJoin(buildingSearchRequest);
        sql+=" where 1 = 1 ";
        sql+=simpleQuery(buildingSearchRequest);
        sql+=specialQuery(buildingSearchRequest);
        sql+=" group by b.building_id ";
        Query query = entityManager.createNativeQuery(sql,BuildingEntity.class);
        return (List<BuildingEntity>) query.getResultList();
    }

    private int countTotalItem(){
        String sql = "Select b.* from building as b";
        Query query = entityManager.createNativeQuery(sql,BuildingEntity.class);
        return query.getResultList().size();

    }
    @Override
    public Page<BuildingEntity> getPageSearchBuilding(HomeBuildingSearchRequest homeBuildingSearchRequest, Pageable pageable) {
        String sql = " Select b.* from building as b";
        sql+=multitableJoin(homeBuildingSearchRequest);
        sql+=" where 1 = 1 ";
        sql+=simpleQuery(homeBuildingSearchRequest);
        sql+=specialQuery(homeBuildingSearchRequest);
        sql+=" group by b.building_id ";
        Query query = entityManager.createNativeQuery(sql,BuildingEntity.class);
        List<BuildingEntity>list = query.setFirstResult((int)pageable.getOffset())
                .setMaxResults(pageable.getPageSize()).getResultList();
        return new PageImpl<>(list,pageable,searchHomeBuilding(homeBuildingSearchRequest).size());
    }
}
