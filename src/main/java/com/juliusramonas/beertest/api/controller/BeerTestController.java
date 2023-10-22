package com.juliusramonas.beertest.api.controller;

import com.juliusramonas.beertest.api.model.LocationForm;
import com.juliusramonas.beertest.api.model.WaypointWebDto;
import com.juliusramonas.beertest.api.service.BeerApiService;
import jakarta.validation.Valid;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class BeerTestController {

    private final BeerApiService beerApiService;

    @GetMapping("/")
    public String showFrom(final Model model) {
        model.addAttribute("locationForm", new LocationForm(51.355468, 11.100790));
        return "new_route";
    }

    @PostMapping("/results")
    public String showList(@Valid @ModelAttribute final LocationForm form, final BindingResult result,
            final Model model) {
        if (result.hasErrors()) {
            return "new_route";
        }

        final var waypoints = beerApiService.getBestRoute(form.latitude(), form.longitude());
        final long totalBeers = waypoints.stream()
                .map(WaypointWebDto::brewery)
                .filter(Objects::nonNull)
                .filter(brewery -> brewery.beers() != null)
                .mapToInt(brewery -> brewery.beers().size())
                .sum();
        model.addAttribute("waypoints", waypoints);
        model.addAttribute("totalBeers", totalBeers);

        return "calculate_waypoints";
    }

}
