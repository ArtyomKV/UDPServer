package com.realtrac.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "device")
@NoArgsConstructor
@Setter
@Getter
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "device_seq")
    @Column(name = "device_id")
    private Long deviceId;

    @Column(name = "mac_address")
    private byte[] mac;

    @OneToMany(mappedBy = "device", fetch = FetchType.LAZY)
    private List<Geodata> geodata;
}
