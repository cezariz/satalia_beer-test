package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

@Entity
@Table(name = "styles")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class Style extends AbstractEntity {

    @Column(name = "cat_id")
    private Long categoryId;

    @Column(name = "style_name")
    private String styleName;

    @Column(name = "last_mod")
    private ZonedDateTime lastModified;

}
