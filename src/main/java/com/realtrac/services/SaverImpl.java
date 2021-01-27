package com.realtrac.services;

import com.realtrac.entities.Device;
import com.realtrac.entities.Geodata;
import com.realtrac.models.UDPPackage;
import com.realtrac.repositories.DeviceRepositories;
import com.realtrac.repositories.GeoDataRepositories;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@Slf4j
@Service
@AllArgsConstructor
public class SaverImpl implements Saver {

    DeviceRepositories deviceRepositories;
    GeoDataRepositories geoDataRepositories;

    @Transactional
    @Override
    public boolean saveToDB(UDPPackage udpPackage){
        log.info("В методе сохранения в базу");
        byte[] mac = udpPackage.getMac();
        Geodata geodata = getGeodata(udpPackage);
        Device deviceFromDB = deviceRepositories.findByMac(mac);
        int packageNumber = udpPackage.getPackageNumber();

        if (!isPackageNumberAndDeviceIsExistInDB(packageNumber, deviceFromDB)) {
            if (deviceFromDB == null) {
                Device device = new Device();
                device.setMac(mac);
                deviceRepositories.save(device);
                geodata.setDevice(device);
            }
            else {
                log.info("Такой device уже присутствует в базе, его mac {}", Arrays.toString(mac));
                geodata.setDevice(deviceFromDB);
            }
            geoDataRepositories.save(geodata);
        }

        return true;
    }

    private boolean isPackageNumberAndDeviceIsExistInDB(int packageNumber, Device device) {
        log.info("Проверяем наличие геоданных с таким номером пакета в базе");
        Geodata geodata = geoDataRepositories.findByPackageNumberAndDevice(packageNumber, device);
        return geodata != null;
    }

    private Geodata getGeodata(UDPPackage udpPackage) {
        Geodata geodata = new Geodata();
        geodata.setPackageNumber(udpPackage.getPackageNumber());
        geodata.setLatitude(udpPackage.getLatitude());
        geodata.setLongitude(udpPackage.getLongitude());
        geodata.setLocationTime(udpPackage.getLocationTime());
        return geodata;
    }
}
