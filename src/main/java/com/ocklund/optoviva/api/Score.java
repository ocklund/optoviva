package com.ocklund.optoviva.api;

import lombok.Data;

@Data
public class Score {
    private String id;
    private String areaId;
    private String categoryId;
    private int score;
    private String created;
    private String modified;
    private String modifiedBy;
}
