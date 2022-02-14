package com.company.entity;

import lombok.Data;

@Data
public class Transaction {

    private Long id;
    private String name;
    private Double amount;
    private String reason;
    private Long userId;
    private Long TransactionId;

}
