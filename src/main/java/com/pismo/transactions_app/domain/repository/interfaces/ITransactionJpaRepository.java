package com.pismo.transactions_app.domain.repository.interfaces;

import com.pismo.transactions_app.domain.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ITransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {

    @Query(value = "select * from transactions where account_id = :accountId and operation_type_id in (1, 2, 3) order by event_date", nativeQuery = true)
    List<TransactionEntity> findDebitOperationsByAccountId(long accountId);
}
