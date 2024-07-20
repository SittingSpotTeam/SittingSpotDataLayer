package com.sittingspot.sittingspotdatalayer.controller;

import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotInDTO;
import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotOutDTO;
import com.sittingspot.sittingspotdatalayer.models.Area;
import com.sittingspot.sittingspotdatalayer.models.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController("/sitting-spot-dl/api/v1")
public class SittingSpotDataLayerController {

    @GetMapping("/")
    public List<SittingSpotOutDTO> getSittingSpots() {

        return List.of();
    }

    @PostMapping("/")
    public SittingSpotOutDTO postSittingSpot(@RequestBody SittingSpotInDTO sittingSpot) {
        return null;
    }

    @GetMapping("/{Id}")
    public SittingSpotOutDTO getSittingSpot(@PathVariable UUID Id) {
        return null;
    }

    @PutMapping("/{Id}")
    public void updateSittingSpot(@PathVariable UUID Id, @RequestBody List<String> labels) {

    }

    @GetMapping("/find")
    public List<SittingSpotOutDTO> findSittingSpots(@RequestParam("location")Area location,
                                                    @RequestParam(value = "tags", required = false)List<Tag> tags,
                                                    @RequestParam(value = "labels",required = false)List<String> labels) {
        return List.of();
    }
}
