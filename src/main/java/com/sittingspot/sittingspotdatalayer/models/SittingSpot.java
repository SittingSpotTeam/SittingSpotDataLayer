package com.sittingspot.sittingspotdatalayer.models;

import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotInDTO;
import com.sittingspot.sittingspotdatalayer.DTO.SittingSpotOutDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "sitting_spot")
public class SittingSpot {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Embedded
    private Location location;

    @ElementCollection
    @CollectionTable(name = "sitting_spot_tags", joinColumns = @JoinColumn(name = "id"))
    private List<Tag> tags = new ArrayList<>();

    @ElementCollection
    @Column(name = "labels")
    @CollectionTable(name = "sitting_spot_labels", joinColumns = @JoinColumn(name = "id"))
    private List<String> labels = new ArrayList<>();

    @Transient
    public SittingSpotOutDTO toOutDTO(){
        return new SittingSpotOutDTO(id, location, tags, labels);
    }

    public SittingSpot(SittingSpotInDTO dto) {
        this.id = dto.id();
        this.location = dto.location();
        this.tags = dto.tags();
    }
}