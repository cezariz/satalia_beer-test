package com.juliusramonas.beertest.service.mapper;

import com.juliusramonas.beertest.dao.model.Beer;
import com.juliusramonas.beertest.service.model.BeerData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface BeerDataMapper {

    BeerData mapToData(Beer source);

}
