package com.realtrac.models;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.util.Arrays;

@Slf4j
@Setter
@Getter
public class UDPPackage {
    private byte[] mac;
    private byte packageType;
    private int packageNumber;
    private double locationTime;
    private long latitude;
    private long longitude;

    public UDPPackage(byte[] packageData) {
        this.mac = Arrays.copyOfRange(packageData, 0, 6);
        packageType = packageData[6];

        byte[] idBuf = Arrays.copyOfRange(packageData, 7, 11);
        ByteBuffer intBuf = ByteBuffer.wrap(idBuf);
        this.packageNumber = intBuf.getInt();

        byte[] timeBuf = Arrays.copyOfRange(packageData, 11, 19);
        ByteBuffer doubleBuf = ByteBuffer.wrap(timeBuf);
        this.locationTime = doubleBuf.getDouble();

        byte[] latitudeBuf = Arrays.copyOfRange(packageData, 19, 27);
        ByteBuffer longBuf = ByteBuffer.wrap(latitudeBuf);
        this.latitude = longBuf.getLong();

        byte[] longitudeBuf = Arrays.copyOfRange(packageData, 27, 35);
        ByteBuffer longBuf2 = ByteBuffer.wrap(longitudeBuf);
        this.longitude = longBuf2.getLong();

        System.out.println("MAC : " + Arrays.toString(mac));
        System.out.println("Тип пакета " + packageType);
        System.out.println("Id " + packageNumber);
        System.out.println("Время " + locationTime);
        System.out.println("Широта " + latitude);
        System.out.println("Долгота " + longitude);
    }
}
