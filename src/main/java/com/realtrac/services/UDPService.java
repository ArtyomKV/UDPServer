package com.realtrac.services;

import com.realtrac.models.UDPPackage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor
public class UDPService implements ApplicationRunner {

    private final static int SERVER_PORT = 7000;
    Saver saverImpl;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        while (true) {
            try {
                DatagramSocket serverSocket = new DatagramSocket(SERVER_PORT);

                ByteBuffer receiveBuf = getByteBuffer();
                ByteBuffer sendBuf = getByteBuffer();
                DatagramPacket inputPacket = getDatagramPacket(receiveBuf);

                log.info("Waiting for a client to connect...");

                serverSocket.receive(inputPacket);

                byte[] inputPacketArray = inputPacket.getData();

                UDPPackage udpPackage = new UDPPackage(inputPacketArray);
                if(saverImpl.saveToDB(udpPackage)) {
                    sendBuf.put(udpPackage.getMac());
                    byte confirmTypePack = 2;
                    sendBuf.put(confirmTypePack);

                    InetAddress senderAddress = inputPacket.getAddress();
                    int senderPort = inputPacket.getPort();

                    DatagramPacket outputPacket = new DatagramPacket(sendBuf.array(), sendBuf.capacity(), senderAddress, senderPort);

                    serverSocket.send(outputPacket);
                }

                serverSocket.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private DatagramPacket getDatagramPacket(ByteBuffer receiveBuf) {
        return new DatagramPacket(receiveBuf.array(), receiveBuf.capacity());
    }

    private ByteBuffer getByteBuffer() {
        ByteBuffer receiveBuf = ByteBuffer.allocate(1024);
        receiveBuf.order(ByteOrder.BIG_ENDIAN);
        return receiveBuf;
    }

}
