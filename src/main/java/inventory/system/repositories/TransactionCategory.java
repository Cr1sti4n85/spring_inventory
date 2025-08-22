package inventory.system.repositories;

import inventory.system.models.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface TransactionCategory extends JpaRepository<Transaction, Long>, JpaSpecificationExecutor<Transaction> {
}
