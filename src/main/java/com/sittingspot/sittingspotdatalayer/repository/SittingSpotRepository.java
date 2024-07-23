package com.sittingspot.sittingspotdatalayer.repository;

import com.sittingspot.sittingspotdatalayer.models.Area;
import com.sittingspot.sittingspotdatalayer.models.SittingSpot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SittingSpotRepository extends JpaRepositoryImplementation<SittingSpot, UUID> {

    @Query(value = "SELECT * from sitting_spot s " +
            "where s.location.x between :area.center.x - :area.range and :area.center.x + :area.range " +
            "and s.location.y between :area.center.y - :area.range and :area.center.y + :area.range ", nativeQuery = true)
    public List<SittingSpot> findByArea(@Param("area") Area area);

}