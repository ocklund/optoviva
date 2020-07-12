package com.ocklund.optoviva.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private String categoryId;
    private Integer score;
    private Integer areaId;
}
