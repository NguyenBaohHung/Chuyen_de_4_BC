package com.group.EstateAngencyProject.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO extends AbstractDTO{
    private Integer transactionId;
    private String note;
    private Integer buildingId;
}
