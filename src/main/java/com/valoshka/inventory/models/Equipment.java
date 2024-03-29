package com.valoshka.inventory.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.List;

@Entity
@Table(name = "equipments")
@Data
@NoArgsConstructor
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @NonNull
    @Column(name = "name")
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "equipment")
    private List<EquipmentCard> equipmentCardList;

    @Override
    public String toString() {
        return "Equipment{" +
                "id=" + id +
                ", name=" + name +
                ", equipmentCardListSize=" + equipmentCardList.size() +
                '}';
    }
}
