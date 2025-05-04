package com.group.EstateAngencyProject.service;

import com.group.EstateAngencyProject.dto.TransactionDTO;
import com.group.EstateAngencyProject.dto.paging.APIPageableDTO;
import com.group.EstateAngencyProject.dto.response.TransactionResponse;
import com.group.EstateAngencyProject.entity.TransactionEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ITransactionService {
     TransactionDTO getTransactionById(Integer transactionId);
     APIPageableDTO getAllTransactionByUserId(Pageable pageable);
     TransactionDTO createOrUpdateTransaction(TransactionDTO transactionDTO);
     void deleteTransaction(Integer id);
     List<TransactionResponse> getAllTransaction(Pageable pageable);
}
