package com.realtrac.services;

import com.realtrac.models.UDPPackage;

public interface Saver {
    boolean saveToDB(UDPPackage udpPackage);
}
