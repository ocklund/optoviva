package com.ocklund.optoviva.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Choice {
    private String categoryId;
    private Integer score;
    private List<String> areaIds = new ArrayList<>();
}
