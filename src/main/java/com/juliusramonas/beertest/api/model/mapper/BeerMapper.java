package com.juliusramonas.beertest.api.model.mapper;

import com.juliusramonas.beertest.api.model.BeerWebDto;
import com.juliusramonas.beertest.service.model.BeerData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BeerMapper {

    BeerWebDto mapDataToDto(BeerData source);

}
