package com.CptFranck.InventoryService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "venue")
public class VenueEntity {

    @Id
    @Column(name = "id")
    private Long id;

    @Id
    @Column(name = "name")
    private String name;

    @Id
    @Column(name = "address")
    private String address;

    @Id
    @Column(name = "total_capacity")
    private Long totalCapacity;
}
