package com.ocklund.optoviva.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Integer.compare;
import static java.lang.Integer.parseInt;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Comparable<Category> {
    private String id;
    private String name;
    private String description;
    private String created;
    private String modified;
    private String modifiedBy;

    @Override
    public int compareTo(Category c) {
        return compare(parseInt(id), parseInt(c.getId()));
    }
}
