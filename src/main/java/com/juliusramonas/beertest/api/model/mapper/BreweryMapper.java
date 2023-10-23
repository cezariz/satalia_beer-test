package com.juliusramonas.beertest.api.model.mapper;

import com.juliusramonas.beertest.api.model.BreweryWebDto;
import com.juliusramonas.beertest.service.model.BreweryData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        uses = BeerMapper.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BreweryMapper {

    BreweryWebDto mapDataToDto(BreweryData source);

}
