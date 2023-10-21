package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "breweries")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Brewery extends AbstractEntity {

    private String name;

    private String address1;

    private String address2;

    private String city;

    private String state;

    private String code;

    private String country;

    private String phone;

    private String website;

    private String filepath;

    @Column(name = "descript", columnDefinition = "text")
    private String description;

    @Column(name = "add_user")
    private Long userId;

    @Column(name = "last_mod")
    private ZonedDateTime lastModified;

    @OneToOne(orphanRemoval = true, mappedBy = "brewery")
    private GeoCode geoCode;

    @OneToMany(mappedBy = "brewery", fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Beer> beers = new HashSet<>();

}
