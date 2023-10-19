package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "geocodes")
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@Data
public class GeoCode extends AbstractEntity {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "brewery_id", referencedColumnName = "id")
    private Brewery brewery;

    @Column(columnDefinition = "numeric(17,14)")
    private BigDecimal latitude;

    @Column(columnDefinition = "numeric(18,14)")
    private BigDecimal longitude;

    private String accuracy;

}
