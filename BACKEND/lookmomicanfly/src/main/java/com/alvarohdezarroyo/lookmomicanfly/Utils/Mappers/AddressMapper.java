package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.AddressDTO;
import com.alvarohdezarroyo.lookmomicanfly.Models.Address;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.AESEncryptionUtil;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Validators.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class AddressMapper {

    @Autowired
    private final UserValidator userValidator;

    public AddressMapper(UserValidator userValidator) {
        this.userValidator = userValidator;
    }

    public static AddressDTO toDTO(Address address) throws Exception {
        final AddressDTO addressDTO=new AddressDTO();
        addressDTO.setId(address.getId());
        addressDTO.setUserId(address.getUserId().getId());
        addressDTO.setFullName(AESEncryptionUtil.decrypt(new String(address.getFullName(), StandardCharsets.UTF_8)));
        addressDTO.setStreet(AESEncryptionUtil.decrypt(new String(address.getStreet(), StandardCharsets.UTF_8)));
        addressDTO.setCity(AESEncryptionUtil.decrypt(new String(address.getCity(), StandardCharsets.UTF_8)));
        addressDTO.setCountry(AESEncryptionUtil.decrypt(new String(address.getCountry(), StandardCharsets.UTF_8)));
        addressDTO.setZipCode(AESEncryptionUtil.decrypt(new String(address.getZipCode(), StandardCharsets.UTF_8)));
        return addressDTO;
    }

    public Address toEntity(AddressDTO addressDTO) throws Exception {
        final Address address=new Address();
        address.setUserId(userValidator.returnUserById(addressDTO.getUserId()));
        address.setActive(true);
        address.setFullName(AESEncryptionUtil.encrypt(addressDTO.getFullName()).getBytes());
        address.setStreet(AESEncryptionUtil.encrypt(addressDTO.getStreet()).getBytes());
        address.setZipCode(AESEncryptionUtil.encrypt(addressDTO.getZipCode()).getBytes());
        address.setCity(AESEncryptionUtil.encrypt(addressDTO.getCity()).getBytes());
        address.setCountry(AESEncryptionUtil.encrypt(addressDTO.getCountry()).getBytes());
        return address;
    }

    public static AddressDTO[] addressListToAddressDTOArray(List<Address> addresses){
        return addresses.stream().map(address -> {
            try {
                return toDTO(address);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).toArray(size-> new AddressDTO[addresses.size()]);
    }

}
