package com.psd.springboot.mapper;

import com.psd.springboot.entity.Transaction;
import com.psd.springboot.dto.TransactionDto;
import org.springframework.security.core.parameters.P;

public class TransactionMapper {

    public static Transaction mapToTransaction(TransactionDto transactionDto){

        Transaction transaction = new Transaction();
        transaction.setId(transactionDto.id());
        transaction.setType(transactionDto.type());
        transaction.setAmount(transactionDto.amount());
        transaction.setTimestamp(transactionDto.timestamp());
        transaction.setRemarks(transactionDto.remarks());
        return transaction;
    }

    public static TransactionDto mapToTransactionDto(Transaction transaction) {

        return new TransactionDto(
                transaction.getId(),
                transaction.getType(),
                transaction.getAmount(),
                transaction.getTimestamp(),
                transaction.getRemarks(), transaction.getAccount() != null ? transaction.getAccount().getId() : null
        );
    }
}
