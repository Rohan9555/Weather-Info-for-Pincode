package com.weather.api.repository;

import com.weather.api.entity.PincodeLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PincodeLocationRepository extends JpaRepository<PincodeLocation, String> {
}