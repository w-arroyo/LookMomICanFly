package com.alvarohdezarroyo.lookmomicanfly.Repositories;

import com.alvarohdezarroyo.lookmomicanfly.Models.BankAccount;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {

    @Query("SELECT account FROM BankAccount account WHERE account.user.id = :id AND account.active= true")
    Optional<BankAccount> findUserActiveBankAccount(@Param("id") String id);

    @Transactional
    @Modifying
    @Query(value = "UPDATE bank_accounts SET active=false WHERE user_id= :id", nativeQuery = true)
    void deactivateAllUserBankAccounts(@Param("id") String id);
}
