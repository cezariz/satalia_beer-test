package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "beers")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Beer extends AbstractEntity {

    @Column(name = "brewery_id")
    private Long breweryId;

    private String name;

    @Column(name = "cat_id")
    private Long categoryId;

    @Column(name = "style_id")
    private Long styleId;

    private String abv;

    private String ibu;

    private String srm;

    private String upc;

    private String filepath;

    @Column(name = "descript", columnDefinition = "text")
    private String description;

    @Column(name = "add_user")
    private String addUser;

    @Column(name = "last_mod")
    private ZonedDateTime lastModified;

}
