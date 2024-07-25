package com.sittingspot.sittingspotdatalayer.DTO;

import com.sittingspot.sittingspotdatalayer.models.Location;
import com.sittingspot.sittingspotdatalayer.models.Tag;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * DTO for {@link com.sittingspot.sittingspotdatalayer.models.SittingSpot}
 */
public record SittingSpotOutDTO(String id, Location location, List<Tag> tags,
                                List<String> labels) implements Serializable {
}