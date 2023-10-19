package com.juliusramonas.beertest.dao;

import com.juliusramonas.beertest.dao.model.Beer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {

}
