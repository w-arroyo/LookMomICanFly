package com.alvarohdezarroyo.lookmomicanfly.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VisitorInfoDTO {

    private String ip, device, browser, operatingSystem, browserType;

}
