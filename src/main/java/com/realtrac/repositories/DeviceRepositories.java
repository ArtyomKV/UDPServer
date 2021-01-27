package com.realtrac.repositories;

import com.realtrac.entities.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepositories extends JpaRepository<Device, Long> {
    public Device findByMac(byte [] mac);
}
