package com.juliusramonas.beertest.dao;

import com.juliusramonas.beertest.dao.model.GeoCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeoCodeRepository extends JpaRepository<GeoCode, Long> {

}
