package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.BankAccountDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.BankAccount;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.AESEncryptionUtil;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class BankAccountMapper {

    public static BankAccount toBankAccount(BankAccountDTO bankAccountDTO, User user) throws Exception {
        final BankAccount bankAccount=new BankAccount();
        bankAccount.setActive(true);
        bankAccount.setNumber(
                AESEncryptionUtil.encrypt(bankAccountDTO.getNumber()).getBytes()
        );
        bankAccount.setUser(user);
        return bankAccount;
    }

    public static BankAccountDTO toDTO(BankAccount bankAccount) throws Exception {
        if(bankAccount==null)
            return null;
        final BankAccountDTO bankAccountDTO=new BankAccountDTO();
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setNumber(
                AESEncryptionUtil.decrypt(new String(bankAccount.getNumber(), StandardCharsets.UTF_8))
        );
        bankAccountDTO.setUserId(bankAccount.getUser().getId());
        return bankAccountDTO;
    }

}
