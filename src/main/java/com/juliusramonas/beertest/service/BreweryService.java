package com.juliusramonas.beertest.service;

import com.juliusramonas.beertest.dao.BreweryRepository;
import com.juliusramonas.beertest.dao.model.Brewery;
import com.juliusramonas.beertest.service.model.Boundary;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BreweryService {

    private final BreweryRepository breweryRepository;

    public List<Brewery> getBreweriesInBoundary(final Boundary boundary) {
        return breweryRepository.getBreweriesInBoundary(boundary.lowerLat(), boundary.upperLat(), boundary.lowerLong(),
                boundary.upperLong());
    }

}
