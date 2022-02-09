package com.javadevjournal.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Employee implements Serializable {

    private static final long serialVersionUID = 1L;

    Integer id;
    String name;
    Double salary;
}
