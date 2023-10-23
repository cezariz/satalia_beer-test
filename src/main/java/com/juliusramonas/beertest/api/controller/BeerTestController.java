package com.juliusramonas.beertest.api.controller;

import com.juliusramonas.beertest.api.model.LocationForm;
import com.juliusramonas.beertest.api.model.WaypointWebDto;
import com.juliusramonas.beertest.api.service.BeerApiService;
import com.juliusramonas.beertest.component.advice.MethodExecutionDurations;
import jakarta.validation.Valid;
import java.util.List;
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
    private final MethodExecutionDurations methodExecutionDurations;

    @GetMapping("/beer-test/")
    public String showFrom(final Model model) {
        model.addAttribute("locationForm", new LocationForm(51.355468, 11.100790, true));
        return "new_route";
    }

    @PostMapping("/beer-test/results")
    public String showList(@Valid @ModelAttribute final LocationForm form, final BindingResult result,
            final Model model) {
        if (result.hasErrors()) {
            return "new_route";
        }

        final List<WaypointWebDto> waypoints;

        if (form.optimize()) {
            waypoints = beerApiService.getBestRouteOptimized(form.latitude(), form.longitude());
            model.addAttribute("timeTaken",
                    methodExecutionDurations.getMethodDurations().get("calculateWaypointsOptimized"));
        } else {
            waypoints = beerApiService.getBestRoute(form.latitude(), form.longitude());
            model.addAttribute("timeTaken", methodExecutionDurations.getMethodDurations().get("calculateWaypoints"));
        }

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
