package com.juliusramonas.beertest.dao;

import com.juliusramonas.beertest.dao.model.Brewery;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BreweryRepository extends JpaRepository<Brewery, Long> {

    @EntityGraph(attributePaths = {"geoCode", "beers"})
    @Query("""
            SELECT DISTINCT b FROM Brewery b
            JOIN b.geoCode g
            WHERE g.latitude BETWEEN :lowerLat AND :upperLat
            AND g.longitude BETWEEN :lowerLong AND :upperLong
            """)
    List<Brewery> getBreweriesInBoundary(@Param("lowerLat") double lowerLat, @Param("upperLat") double upperLat,
                                         @Param("lowerLong") double lowerLong, @Param("upperLong") double upperLong);

}
