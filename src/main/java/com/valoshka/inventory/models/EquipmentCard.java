package com.valoshka.inventory.models;

import com.valoshka.inventory.models.compositeKey.EquipmentCardKey;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;

@Entity
@Table(name = "equipment_cards")
@NoArgsConstructor
@Data
public class EquipmentCard {

    @EmbeddedId
    private EquipmentCardKey id;

    @Column(name = "equip_count")
    private int equipCount;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("equipmentId")
    @JoinColumn(name = "equip_id")
    private Equipment equipment;

    @ManyToOne(cascade = CascadeType.ALL)
    @MapsId("waybillId")
    @JoinColumn(name = "waybill_id")
    private Waybill waybill;

    public EquipmentCard(int equipCount,
                         @NonNull Equipment equipment,
                         @NonNull Waybill waybill) {

        this.id = new EquipmentCardKey(equipment.getId(), waybill.getId());
        this.equipCount = equipCount;
        this.equipment = equipment;
        this.waybill = waybill;
    }

    public void setId(int waybillId, int equipmentId) {
        this.id = new EquipmentCardKey(waybillId, equipmentId);
    }

    public void setEquipment(@NonNull Equipment equipment) {
        this.equipment = equipment;
        if (equipment.getEquipmentCardList() == null) {
            equipment.setEquipmentCardList(new ArrayList<>());
        }
        equipment.getEquipmentCardList().add(this);
    }

    public void setWaybill(@NonNull Waybill waybill) {
        this.waybill = waybill;
        if (waybill.getEquipmentCardList() == null) {
            waybill.setEquipmentCardList(new ArrayList<>());
        }
        waybill.getEquipmentCardList().add(this);
    }
}
