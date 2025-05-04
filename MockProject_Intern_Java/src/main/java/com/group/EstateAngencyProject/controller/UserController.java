package com.group.EstateAngencyProject.controller;


import com.group.EstateAngencyProject.constant.PageConstant;
import com.group.EstateAngencyProject.dto.TransactionDTO;
import com.group.EstateAngencyProject.dto.UserDTO;
import com.group.EstateAngencyProject.message.GlobalMessage;
import com.group.EstateAngencyProject.response.APIResponse;
import com.group.EstateAngencyProject.service.ITransactionService;
import com.group.EstateAngencyProject.service.impl.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-manage")
@AllArgsConstructor
public class UserController {
    private final UserService userService;
    private final AuthenticationManager authentication;
    private final ITransactionService transactionService;

    @PostMapping
    public ResponseEntity<Object> UpdateCustomer(@RequestBody UserDTO customerDTO){
        return APIResponse.responseBuilder(HttpStatus.CREATED, GlobalMessage.SUCCESS,userService.createOrUpdateUser(customerDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCustomer(@PathVariable Integer id){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,userService.deleteCustomer(id));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findUserById(@PathVariable("id") Integer id){
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,userService.findById(id));
    }

    @GetMapping("/list")
    public ResponseEntity<Object> getAllUser(@RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_NUMBER) int page,
                                             @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_SIZE) int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,userService.getAllUsers(pageable));
    }

    @GetMapping("/transaction-list")
    public ResponseEntity<Object> getAllTransaction(@RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_NUMBER) int page,
                                                    @RequestParam(defaultValue = PageConstant.DEFAULT_PAGE_SIZE) int pageSize){
        Pageable pageable = PageRequest.of(page,pageSize);
        return APIResponse.responseBuilder(HttpStatus.OK,GlobalMessage.SUCCESS,transactionService.getAllTransaction(pageable));
    }

    @DeleteMapping("/del/{id}")
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
