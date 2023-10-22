package com.juliusramonas.beertest.service.mapper;

import com.juliusramonas.beertest.dao.model.Brewery;
import com.juliusramonas.beertest.service.model.Point;
import com.juliusramonas.beertest.service.model.WaypointData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = BreweryDataMapper.class)
public interface WaypointDataMapper {

    @Mapping(target = "lat", source = "point.lat")
    @Mapping(target = "lon", source = "point.lon")
    @Mapping(target = "distanceTraveled", source = "point.distanceTraveled")
    @Mapping(target = "distanceToPreviousPoint", source = "point.distanceToPreviousPoint")
    WaypointData breweryAndPointToWaypointData(Brewery brewery, Point point);

}
