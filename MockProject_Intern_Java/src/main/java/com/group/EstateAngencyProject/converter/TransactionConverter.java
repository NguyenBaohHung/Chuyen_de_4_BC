package com.group.EstateAngencyProject.converter;

import com.group.EstateAngencyProject.dto.TransactionDTO;
import com.group.EstateAngencyProject.entity.TransactionEntity;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class TransactionConverter {
    private ModelMapper modelMapper;

    public TransactionDTO toTransactionDTO(TransactionEntity transactionEntity){
        return modelMapper.map(transactionEntity, TransactionDTO.class);
    }

    public TransactionEntity toTransactionEntity(TransactionDTO transactionDTO){
        return modelMapper.map(transactionDTO, TransactionEntity.class);
    }
}
