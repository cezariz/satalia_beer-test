package com.juliusramonas.beertest.api.model.mapper;

import com.juliusramonas.beertest.api.model.BreweryWebDto;
import com.juliusramonas.beertest.service.model.BreweryData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BeerMapper.class)
public interface BreweryMapper {

    BreweryWebDto mapDataToDto(BreweryData source);

}
