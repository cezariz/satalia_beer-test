package com.juliusramonas.beertest.dao;

import com.juliusramonas.beertest.dao.model.Brewery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BreweryRepository extends JpaRepository<Brewery, Long> {

}
