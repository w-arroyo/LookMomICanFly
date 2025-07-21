package com.alvarohdezarroyo.lookmomicanfly.Utils.Mappers;

import com.alvarohdezarroyo.lookmomicanfly.DTO.UserDTO;
import com.alvarohdezarroyo.lookmomicanfly.DTO.UserProfileDTO;
import com.alvarohdezarroyo.lookmomicanfly.Enums.UserType;
import com.alvarohdezarroyo.lookmomicanfly.Models.User;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.AESEncryptionUtil;
import com.alvarohdezarroyo.lookmomicanfly.Utils.DataSafety.PasswordUtils;
import com.alvarohdezarroyo.lookmomicanfly.Utils.Generators.DateGenerator;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class UserMapper {

    public static UserDTO toDTO(User user) throws Exception {
        final UserDTO userDTO=new UserDTO();
        userDTO.setEmail(user.getEmail());
        userDTO.setUserType(user.getUserType().name());
        userDTO.setId(user.getId());
        userDTO.setName(AESEncryptionUtil.decrypt(new String(user.getName(), StandardCharsets.UTF_8)));
        return userDTO;
    }
    public static User toUser(UserDTO userDTO) throws Exception {
        final User user=new User();
        user.setActive(true);
        user.setEmail(userDTO.getEmail());
        user.setUserType(UserType.getUserType(userDTO.getUserType()));
        user.setPassword(PasswordUtils.hashPassword(userDTO.getPassword()));
        user.setName(AESEncryptionUtil.encrypt(userDTO.getName()).getBytes());
        return user;
    }

    public static UserProfileDTO toProfileDTO(User user) throws Exception {
        final UserProfileDTO userProfileDTO=new UserProfileDTO();
        userProfileDTO.setEmail(user.getEmail());
        userProfileDTO.setName(AESEncryptionUtil.decrypt(new String(user.getName(), StandardCharsets.UTF_8)));
        userProfileDTO.setRegistrationDate(DateGenerator.formatDate(user.getRegistrationDate()));
        return userProfileDTO;
    }

}
