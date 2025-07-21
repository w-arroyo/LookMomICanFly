package com.alvarohdezarroyo.lookmomicanfly.RequestDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class ChangePasswordRequestDTO {
    private String id, oldPassword, newPassword;
}
