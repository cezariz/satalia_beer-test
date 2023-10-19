package com.juliusramonas.beertest;

import com.juliusramonas.beertest.dao.GeoCodeRepository;
import com.juliusramonas.beertest.dao.model.GeoCode;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class GeoCodeService {

    private final GeoCodeRepository geoCodeRepository;

    public GeoCode getById(final Long id) {
        return geoCodeRepository.findById(id)
                                .orElseThrow(() -> new EntityNotFoundException(
                                        "Could not find geocode with id=%d".formatted(id)));
    }

}
