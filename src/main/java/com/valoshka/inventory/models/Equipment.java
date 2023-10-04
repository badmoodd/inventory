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
    private int id;

    @NonNull
    @Enumerated(EnumType.STRING)
    @Column(name = "name")
    private EquipmentType name;

    @OneToMany(mappedBy = "equipment")
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
