package com.juliusramonas.beertest.dao.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "geocodes")
@EqualsAndHashCode(exclude = "brewery", callSuper = true)
@NoArgsConstructor
@Data
@ToString(exclude = "brewery", callSuper = true)
public class GeoCode extends AbstractEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brewery_id")
    private Brewery brewery;

    @Column(columnDefinition = "numeric(17,14)")
    private Double latitude;

    @Column(columnDefinition = "numeric(18,14)")
    private Double longitude;

    private String accuracy;

}
