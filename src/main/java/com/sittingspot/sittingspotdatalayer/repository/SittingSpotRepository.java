package com.sittingspot.sittingspotdatalayer.repository;

import com.sittingspot.sittingspotdatalayer.models.Area;
import com.sittingspot.sittingspotdatalayer.models.SittingSpot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SittingSpotRepository extends JpaRepositoryImplementation<SittingSpot, String> {

    @Query(value = "SELECT * from sitting_spot s " +
            "where s.location.x between :x - :range and :x + :range " +
            "and s.location.y between :y - :range and :y + :range ", nativeQuery = true)
    public List<SittingSpot> findByArea(@Param("x") Double x,@Param("y") Double y,@Param("range") Double range);

}