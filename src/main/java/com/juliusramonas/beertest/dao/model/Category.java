package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Category extends AbstractEntity {

    @Column(name = "cat_name")
    private String categoryName;

    @Column(name = "last_mod")
    private ZonedDateTime lastModified;

}
