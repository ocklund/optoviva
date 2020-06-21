package com.ocklund.optoviva.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Score {
    private String id;
    private String areaId;
    private String categoryId;
    private String locationId;
    private Integer score;
    private String created;
    private String modified;
    private String modifiedBy;
}
