package com.ocklund.optoviva.api;

import lombok.Data;

@Data
public class Category {
    private String id;
    private String type;
    private String description;
    private String created;
    private String modified;
    private String modifiedBy;
}
