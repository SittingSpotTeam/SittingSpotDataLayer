package com.sittingspot.sittingspotdatalayer.repository;

import com.sittingspot.sittingspotdatalayer.models.SittingSpot;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

import java.util.UUID;

public interface SittingSpotRepository extends JpaRepositoryImplementation<SittingSpot, UUID> {
}