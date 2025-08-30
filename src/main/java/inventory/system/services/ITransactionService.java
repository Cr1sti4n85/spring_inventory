package inventory.system.services;

import inventory.system.dto.Response;
import inventory.system.dto.TransactionRequest;
import inventory.system.enums.TransactionStatus;

public interface ITransactionService {

    Response purchase(TransactionRequest transactionRequest);

    Response sell(TransactionRequest transactionRequest);

    Response returnToSupplier(TransactionRequest transactionRequest);

    Response getAllTransactions(int page, int size, String filter);

    Response getAllTransactionById(Long id);

    Response getAllTransactionByMonthAndYear(int month, int year);

    Response updateTransactionStatus(Long transactionId, TransactionStatus status);
}
