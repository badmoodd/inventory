package com.valoshka.inventory.models;

import com.valoshka.inventory.models.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "equipment")
@Data
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    int id;

    @NonNull
    @Column(name = "name")
    EquipmentType name;

    @ManyToMany
    @JoinTable(
            name = "equipment_card",
            joinColumns = @JoinColumn(name = "equip_id"),
            inverseJoinColumns = @JoinColumn(name = "waybill_id")
    )
    List<Waybill> waybillList;

}
