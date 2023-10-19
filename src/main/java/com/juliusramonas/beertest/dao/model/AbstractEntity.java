package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Data;
import lombok.EqualsAndHashCode;

@MappedSuperclass
@Data
@EqualsAndHashCode
public class AbstractEntity {

    @Id
    private Long id;

}
