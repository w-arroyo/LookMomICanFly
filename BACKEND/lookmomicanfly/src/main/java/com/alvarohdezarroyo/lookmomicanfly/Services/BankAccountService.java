package com.alvarohdezarroyo.lookmomicanfly.Services;

import com.alvarohdezarroyo.lookmomicanfly.Exceptions.EntityNotFoundException;
import com.alvarohdezarroyo.lookmomicanfly.Models.BankAccount;
import com.alvarohdezarroyo.lookmomicanfly.Repositories.BankAccountRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankAccountService {

    @Autowired
    private final BankAccountRepository bankAccountRepository;

    public BankAccountService(BankAccountRepository bankAccountRepository) {
        this.bankAccountRepository = bankAccountRepository;
    }

    @Transactional
    public BankAccount saveBankAccount(BankAccount bankAccount){
        return bankAccountRepository.save(bankAccount);
    }

    public BankAccount findById(String id){
        return bankAccountRepository.findById(id).orElseThrow(
                ()->new EntityNotFoundException("Bank Account ID does not exist.")
        );
    }

    public BankAccount findUserActiveAccount(String userId){
        return bankAccountRepository.findUserActiveBankAccount(userId)
                .orElse(null);
    }

    @Transactional
    public void deactivateAllUserBankAccounts(String userId){
        bankAccountRepository.deactivateAllUserBankAccounts(userId);
    }

}
