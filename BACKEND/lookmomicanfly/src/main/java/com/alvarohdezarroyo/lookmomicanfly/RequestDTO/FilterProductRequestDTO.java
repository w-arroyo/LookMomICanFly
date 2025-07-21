package com.alvarohdezarroyo.lookmomicanfly.RequestDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FilterProductRequestDTO {

    private List<String> colors,manufacturers,subcategories;
    private List<Integer> years;

}
