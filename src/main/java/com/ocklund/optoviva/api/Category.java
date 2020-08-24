package com.ocklund.optoviva.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static java.lang.Long.compare;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Comparable<Category> {
    private Long id;
    private String name;
    private String description;

    @Override
    public int compareTo(Category c) {
        return compare(id, c.getId());
    }

    public String toJson() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            return null;
        }
    }
}
