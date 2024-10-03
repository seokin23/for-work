package project.forwork.api.domain.transaction.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.forwork.api.common.error.TransactionErrorCode;
import project.forwork.api.common.exception.ApiException;
import project.forwork.api.domain.transaction.model.Transaction;
import project.forwork.api.domain.transaction.service.port.TransactionRepository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class TransactionRepositoryImpl implements TransactionRepository {

    private final TransactionJpaRepository transactionJpaRepository;

    @Override
    public Transaction save(Transaction tx) {
        return transactionJpaRepository.save(TransactionEntity.from(tx)).toModel();
    }

    @Override
    public Transaction getByOrderId(Long orderId) {
        return findByOrderId(orderId).orElseThrow(() -> new ApiException(TransactionErrorCode.TX_AlREADY_CHARGE));
    }

    @Override
    public boolean existsByOrderTxId(String orderTxId) {
        return transactionJpaRepository.existsByOrderTxId(orderTxId);
    }
}
