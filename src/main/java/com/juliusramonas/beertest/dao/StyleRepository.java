package com.juliusramonas.beertest.dao;

import com.juliusramonas.beertest.dao.model.Style;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StyleRepository extends JpaRepository<Style, Long> {

}
