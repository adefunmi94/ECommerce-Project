package com.personalproject.ecommerce.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
public class Country {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private  String code;

    private String name;

    @OneToMany(mappedBy = "country")
    @JsonIgnore
    private List<State> state;

}
