package com.group.EstateAngencyProject.controller;

import com.group.EstateAngencyProject.constant.PageConstant;
import com.group.EstateAngencyProject.dto.BuildingDTO;
import com.group.EstateAngencyProject.entity.BuildingEntity;
import com.group.EstateAngencyProject.message.GlobalMessage;
import com.group.EstateAngencyProject.response.APIResponse;
import com.group.EstateAngencyProject.service.IBuildingService;
import io.swagger.v3.oas.models.responses.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/api/building")
@AllArgsConstructor
public class BuildingController {
    private final IBuildingService buildingService;


    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteBuilding(@PathVariable Integer id){
//        List<BuildingDTO> results = new ArrayList<>();
//        for(Integer item : ids){
//            BuildingDTO buildingDTO = buildingService.findById(item);
//            results.add(buildingDTO);
//        }
//        if(ids.size()>0){
//            buildingService.deleteBuilding(ids);
//        }
//        if (ids.isEmpty()) {
//            return ResponseEntity.badRequest().body("No IDs provided");
//        }
        BuildingDTO buildingdelete = buildingService.findById(id);
        buildingService.deleteBuilding(id);
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS,buildingdelete);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findBuildingId(@PathVariable Integer id){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,buildingService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Object> createOrUpdateBuilding(@RequestBody BuildingDTO buildingDTO){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,buildingService.createOrUpdateBuilding(buildingDTO));
    }

    @GetMapping("/building-list")
    public ResponseEntity<Object> gettAllBuilding(@RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_NUMBER) int page,
                                                  @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_SIZE) int pageSize,
                                                  @RequestParam(defaultValue = PageConstant.DEFAULT_SORT_BY) String sortBy){
        Pageable pageable = PageRequest.of(page,pageSize, Sort.by(sortBy));
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,buildingService.getAllBuildingDashBoard(pageable));
    }
}
