package com.realtrac.repositories;

import com.realtrac.entities.Device;
import com.realtrac.entities.Geodata;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GeoDataRepositories extends JpaRepository<Geodata, Long> {
    Geodata findByPackageNumberAndDevice(Integer number, Device device);
}
