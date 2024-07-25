package com.sittingspot.sittingspotdatalayer.controller;

import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotInDTO;
import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotOutDTO;
import com.sittingspot.sittingspotdatalayer.models.Area;
import com.sittingspot.sittingspotdatalayer.models.SittingSpot;
import com.sittingspot.sittingspotdatalayer.models.Tag;
import com.sittingspot.sittingspotdatalayer.repository.SittingSpotRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ClientHttpResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController("/api/v1")
public class SittingSpotDataLayerController {

    private SittingSpotRepository sittingSpotRepository;

    @GetMapping("/")
    public List<SittingSpotOutDTO> getSittingSpots() {
        return sittingSpotRepository.findAll().stream().map(SittingSpot::toOutDTO).toList();
    }

    @PostMapping("/")
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
    public List<SittingSpotOutDTO> findSittingSpots(@RequestParam("location")Area location,
                                                    @RequestParam(value = "tags", required = false)List<Tag> tags,
                                                    @RequestParam(value = "labels",required = false)List<String> labels) {
        return sittingSpotRepository.findByArea(location).stream().filter(x ->
             (new HashSet<>(x.getTags()).containsAll(tags) && new HashSet<>(x.getLabels()).containsAll(labels))
        ).map(SittingSpot::toOutDTO).toList();
    }
}
