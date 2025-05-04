package com.group.EstateAngencyProject.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionResponse {
    private Integer transactionId;
    private String note;
    private String buildingName;
    private String userName;
    private String userPhone;
}
