package com.ocklund.optoviva.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    private String id;
    private String name;
    private String description;
    private String created;
    private String modified;
    private String modifiedBy;
}
