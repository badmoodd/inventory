package com.valoshka.inventory.models;

import com.valoshka.inventory.models.enums.EquipmentType;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

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

}
