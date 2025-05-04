package com.group.EstateAngencyProject.controller;

import com.group.EstateAngencyProject.constant.PageConstant;
import com.group.EstateAngencyProject.dto.BuildingDTO;
import com.group.EstateAngencyProject.dto.TransactionDTO;
import com.group.EstateAngencyProject.message.GlobalMessage;
import com.group.EstateAngencyProject.response.APIResponse;
import com.group.EstateAngencyProject.service.ITransactionService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/customer")
@AllArgsConstructor
public class CustomerController {
    private final ITransactionService transactionService;

    @GetMapping("/transaction/{id}")
    public ResponseEntity<Object> getTransactionById(@PathVariable Integer id){
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS,transactionService.getTransactionById(id));
    }

    @GetMapping("/transaction-list")
    public ResponseEntity<Object> getAllTransaction(@RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_SIZE) int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,transactionService.getAllTransactionByUserId(pageable));
    }

    @PostMapping
    public ResponseEntity<Object> createOrUpdateTransaction(@RequestBody TransactionDTO transactionDTO){
        return APIResponse.responseBuilder(HttpStatus.CREATED,GlobalMessage.SUCCESS,transactionService.createOrUpdateTransaction(transactionDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTransaction(@PathVariable Integer id){
//        List<TransactionDTO> results = new ArrayList<>();
//        for(Integer item : ids){
//            TransactionDTO transactionDTO = transactionService.getTransactionById(item);
//            results.add(transactionDTO);
//        }
//        if(ids.size()>0){
//            transactionService.deleteTransaction(ids);
//        }
//        if (ids.isEmpty()) {
//            return ResponseEntity.badRequest().body("No IDs provided");
//        }
        TransactionDTO transactionDTO = transactionService.getTransactionById(id);
        transactionService.deleteTransaction(id);
        return APIResponse.responseBuilder(HttpStatus.OK, GlobalMessage.SUCCESS,transactionDTO);
    }
}
