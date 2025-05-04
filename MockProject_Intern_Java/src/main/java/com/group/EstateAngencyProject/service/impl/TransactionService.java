package com.group.EstateAngencyProject.service.impl;

import com.group.EstateAngencyProject.converter.TransactionConverter;
import com.group.EstateAngencyProject.dto.TransactionDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.dto.response.TransactionResponse;
import com.group.EstateAngencyProject.entity.BuildingEntity;
import com.group.EstateAngencyProject.entity.TransactionEntity;
import com.group.EstateAngencyProject.entity.UserEntity;
import com.group.EstateAngencyProject.repository.BuildingRepository;
import com.group.EstateAngencyProject.repository.TransactionRepository;
import com.group.EstateAngencyProject.repository.UserRepository;
import com.group.EstateAngencyProject.service.ITransactionService;
import com.group.EstateAngencyProject.utils.SecurityUtils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TransactionService implements ITransactionService {
    private final TransactionRepository transactionRepository;
    private final TransactionConverter transactionConverter;
    private final UserRepository userRepository;
    private final BuildingRepository buildingRepository;

    @Override
    public TransactionDTO getTransactionById(Integer transactionId) {
        TransactionEntity transactionEntity = transactionRepository.findById(transactionId).get();
        return transactionConverter.toTransactionDTO(transactionEntity);
    }

    @Override
    public APIPageableDTO getAllTransactionByUserId(Pageable pageable) {
        String userName = SecurityUtils.getPrincipal();
        UserEntity userEntity = userRepository.findByUserNameAndIsActive(userName,true).get();
       Page<TransactionEntity> transactionEntityPage = transactionRepository.findByUserEntity(userEntity,pageable);
       List<TransactionDTO> contents = transactionEntityPage.stream().map(transactionConverter::toTransactionDTO).collect(Collectors.toList());
       APIPageableDTO apiPageableDTO = new APIPageableDTO();
       apiPageableDTO.responsePageBuilder(transactionEntityPage,contents);
       return apiPageableDTO;
    }

    @Override
    public TransactionDTO createOrUpdateTransaction(TransactionDTO transactionDTO) {
        String userName = SecurityUtils.getPrincipal();
        UserEntity userEntity = userRepository.findByUserNameAndIsActive(userName,true).get();
        TransactionEntity transactionEntity = null;
        if(transactionDTO.getTransactionId()==null){
            transactionEntity = transactionConverter.toTransactionEntity(transactionDTO);
            BuildingEntity buildingEntity = buildingRepository.findById(transactionDTO.getBuildingId()).get();
            transactionEntity.setBuildingEntity(buildingEntity);
        }else{
            transactionEntity = transactionRepository.findById(transactionDTO.getTransactionId()).get();
            transactionEntity.setNote(transactionDTO.getNote());
        }
        transactionEntity.setUserEntity(userEntity);
        transactionRepository.save(transactionEntity);
        return transactionConverter.toTransactionDTO(transactionEntity);
    }

    @Transactional
    @Override
    public void deleteTransaction(Integer id) {
//        transactionRepository.deleteByTransactionIdIn(ids);
        transactionRepository.deleteById(id);
    }

    @Override
    public List<TransactionResponse> getAllTransaction(Pageable pageable) {
        Page<TransactionEntity> transactionEntityPage = transactionRepository.findAll(pageable);
        List<TransactionResponse> result = new ArrayList<>();
        for(TransactionEntity item : transactionEntityPage.stream().toList()){
            TransactionResponse transactionResponse = new TransactionResponse();
            transactionResponse.setTransactionId(item.getTransactionId());
            transactionResponse.setNote(item.getNote());
            transactionResponse.setBuildingName(item.getBuildingEntity().getBuildingName());
            transactionResponse.setUserPhone(item.getUserEntity().getPhone());
            transactionResponse.setUserName(item.getUserEntity().getFullName());
//            transactionResponse
            result.add(transactionResponse);
        }
        return result;
    }

}
