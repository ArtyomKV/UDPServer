package com.realtrac.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "geo_data")
public class Geodata {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "geo_data_seq")
    @Column(name = "geo_id")
    private Long id;

    @Column(name = "package_number")
    private Integer packageNumber;
    @Column(name = "location_time")
    private Double locationTime;
    @Column(name = "latitude")
    private Long latitude;
    @Column(name = "longitude")
    private Long longitude;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "device_id")
    Device device;
}
