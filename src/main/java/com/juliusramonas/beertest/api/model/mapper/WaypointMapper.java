package com.juliusramonas.beertest.api.model.mapper;

import com.juliusramonas.beertest.api.model.WaypointWebDto;
import com.juliusramonas.beertest.service.model.WaypointData;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BreweryMapper.class)
public interface WaypointMapper {

    WaypointWebDto mapDataToDto(WaypointData source);


}
