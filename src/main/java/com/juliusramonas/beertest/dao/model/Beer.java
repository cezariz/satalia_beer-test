package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.ZonedDateTime;

@Entity
@Table(name = "beers")
@EqualsAndHashCode(exclude = "brewery", callSuper = true)
@NoArgsConstructor
@Data
@ToString(exclude = "brewery", callSuper = true)
public class Beer extends AbstractEntity {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brewery_id")
    private Brewery brewery;

}
