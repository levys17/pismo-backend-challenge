package com.pismo.transactions_app.domain.repository.interfaces;

import com.pismo.transactions_app.domain.entities.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ITransactionJpaRepository extends JpaRepository<TransactionEntity, Long> {
}
