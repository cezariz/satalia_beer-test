package com.juliusramonas.beertest.service.mapper;

import com.juliusramonas.beertest.dao.model.Brewery;
import com.juliusramonas.beertest.service.model.BreweryData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BeerDataMapper.class)
public interface BreweryDataMapper {

    BreweryData mapToData(Brewery source);

}
