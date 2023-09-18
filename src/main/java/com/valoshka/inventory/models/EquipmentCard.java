package com.valoshka.inventory.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "equipment_card")
@Data
@NoArgsConstructor
public class EquipmentCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @Column(name = "equip_count")
    int equipCount;

    int equipId;

    int waybillId;
}
