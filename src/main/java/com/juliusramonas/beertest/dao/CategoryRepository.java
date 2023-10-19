package com.juliusramonas.beertest.dao;

import com.juliusramonas.beertest.dao.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
