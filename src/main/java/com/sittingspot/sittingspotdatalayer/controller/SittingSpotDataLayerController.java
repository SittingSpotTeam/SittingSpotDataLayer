package com.sittingspot.sittingspotdatalayer.controller;

import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotInDTO;
import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotOutDTO;
import com.sittingspot.sittingspotdatalayer.models.Area;
import com.sittingspot.sittingspotdatalayer.models.Location;
import com.sittingspot.sittingspotdatalayer.models.SittingSpot;
import com.sittingspot.sittingspotdatalayer.models.Tag;
import com.sittingspot.sittingspotdatalayer.repository.SittingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class SittingSpotDataLayerController {

    private SittingSpotRepository sittingSpotRepository;

    @GetMapping
    public List<SittingSpotOutDTO> getSittingSpots() {
        return sittingSpotRepository.findAll().stream().map(SittingSpot::toOutDTO).toList();
    }

    @PostMapping
    public SittingSpotOutDTO postSittingSpot(@RequestBody SittingSpotInDTO sittingSpot) {
        sittingSpotRepository.findById(sittingSpot.id()).ifPresent(x -> {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Sitting spot already exists");
        });
        var newSittingSpot = new SittingSpot(sittingSpot);
        sittingSpotRepository.save(newSittingSpot);
        return newSittingSpot.toOutDTO();
    }

    @GetMapping("/{Id}")
    public SittingSpotOutDTO getSittingSpot(@PathVariable String Id) {
        return sittingSpotRepository.findById(Id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sitting spot not found")
        ).toOutDTO();
    }

    @PutMapping("/{Id}")
    public void updateSittingSpot(@PathVariable String Id, @RequestBody List<String> labels) {
        var sittingSpot = sittingSpotRepository.findById(Id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Sitting spot not found")
        );
        sittingSpot.setLabels(labels);
        sittingSpotRepository.save(sittingSpot);
    }

    @GetMapping("/find")
    public List<SittingSpotOutDTO> findSittingSpots(@RequestParam("x") Double x,
                                                    @RequestParam("y") Double y,
                                                    @RequestParam("area") Double area,
                                                    @RequestParam(value = "tags", required = false)List<Tag> tags,
                                                    @RequestParam(value = "labels",required = false)List<String> labels) {
        if(tags == null){ tags = new ArrayList<>(); }
        if(labels == null){ labels = new ArrayList<>(); }

        var location = new Area(new Location(x,y),area);
        //copies to use in lambda
        List<Tag> finalTags = tags;
        List<String> finalLabels = labels;
        return sittingSpotRepository.findAll().stream()
                .filter(e -> distFrom(e.getLocation().y(),e.getLocation().x(),y,x)*1000 < area)
                .filter(e ->
             (new HashSet<>(e.getTags()).containsAll(finalTags) && new HashSet<>(e.getLabels()).containsAll(finalLabels))
        ).map(SittingSpot::toOutDTO).toList();
    }

    //return distance between two coordinates in km
    public static double distFrom(double lat1, double lng1, double lat2, double lng2) {
        double earthRadius = 6371.0; // miles (or 6371.0 kilometers)
        double dLat = Math.toRadians(lat2-lat1);
        double dLng = Math.toRadians(lng2-lng1);
        double sindLat = Math.sin(dLat / 2);
        double sindLng = Math.sin(dLng / 2);
        double a = Math.pow(sindLat, 2) + Math.pow(sindLng, 2)
                * Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2));
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        double dist = earthRadius * c;

        return dist;
    }
}
